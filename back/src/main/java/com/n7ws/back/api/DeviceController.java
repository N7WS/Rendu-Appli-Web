package com.n7ws.back.api;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n7ws.back.entity.DeviceEntity;
import com.n7ws.back.entity.ScriptEntity;
import com.n7ws.back.entity.ServiceEntity;
import com.n7ws.back.mapper.DeviceMapper;
import com.n7ws.back.model.DeviceModel;
import com.n7ws.back.model.ScriptModel;
import com.n7ws.back.model.ServiceModelPost;
import com.n7ws.back.repository.DeviceRepository;
import com.n7ws.back.repository.ServiceRepository;
import com.n7ws.back.service.TaskService;
import com.n7ws.back.tasks.Task;


/**
 * This class is a REST controller that handles HTTP requests related to devices.
 * It provides endpoints to retrieve all devices and a specific device by its name.
 * Routes:
 * - GET /devices: Retrieve all devices
 * - GET /devices/{name}: Retrieve a specific device by its name
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    DeviceRepository repository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    private TaskService taskService;

    // What is a task ?
    // Task : name, script (name?) ?

    @GetMapping
    public Collection<DeviceModel> devices() {
        return repository.findAll().stream()
			.map(device -> DeviceMapper.toDomain(device))
            .map(device -> DeviceMapper.toModel(device))
            .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public DeviceModel device(@PathVariable String name) {
        return repository.findById(name)
            .map(DeviceMapper::toDomain)
            .map(DeviceMapper::toModel)
            .orElse(null);
    }

    @PostMapping("/add")
	public ResponseEntity<?> addDevice(@RequestBody DeviceModel deviceModel) {
		DeviceEntity deviceEntity = DeviceMapper.toEntity(deviceModel);
        boolean registered;
		try {
            DeviceEntity entity = repository.save(deviceEntity);
            registered = !entity.getUid().equals(null); // entity has a uid
		} catch (Exception e) {
			registered = false;
		}
		return ResponseEntity.ok(registered);
	}

    @PostMapping("/{name}/addService")
	public ResponseEntity<?> addService(@PathVariable String name, @RequestBody String serviceId) {

        System.out.println("Adding service with ID: " + serviceId + " to device: " + name);

        DeviceEntity deviceEntity = repository.findById(name).orElse(null);

        if (deviceEntity == null) {
            System.out.println("Device not found: " + name);
            return ResponseEntity.badRequest().body("Device not found");
        }

        ServiceEntity serviceEntity = serviceRepository.findById(serviceId).orElse(null);

        if (serviceEntity == null) {
            System.out.println("Service not found: " + serviceId);
            return ResponseEntity.badRequest().body("Service not found");
        }

        serviceEntity.setDevices(
            serviceEntity.getDevices() == null ?
                Stream.of(deviceEntity).collect(Collectors.toList()) :
                Stream.concat(serviceEntity.getDevices().stream(), Stream.of(deviceEntity)).collect(Collectors.toList())
        );

		return ResponseEntity.ok(serviceRepository.save(serviceEntity));
	}

    @PostMapping("/{name}/addTask")
    public ResponseEntity<?> addTask(@PathVariable String name, @RequestBody Task task) {
        // Add the new task to name's list of task
        System.out.println(task);

        taskService.addTask(name, new Task(task.name(), task.script_path()));
		return ResponseEntity.ok("task added");
    }

    @GetMapping("/{name}/getTask")
    public ResponseEntity<?> getTask(@PathVariable String name) {
        // get name's first task
        Task toSend = taskService.getPriorityTask(name);

        String json = "{}";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            json = mapper.writeValueAsString(toSend);
        } catch (Exception e) {
            System.err.println(e);
        }
		return ResponseEntity.ok(json);
    }
}