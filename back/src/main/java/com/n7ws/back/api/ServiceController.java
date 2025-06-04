package com.n7ws.back.api;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n7ws.back.entity.ScriptEntity;
import com.n7ws.back.entity.ServiceEntity;
import com.n7ws.back.mapper.ScriptMapper;
import com.n7ws.back.mapper.ServiceMapper;
import com.n7ws.back.model.ScriptModel;
import com.n7ws.back.model.ServiceModel;
import com.n7ws.back.model.ServiceModelPost;
import com.n7ws.back.repository.ScriptRepository;
import com.n7ws.back.repository.ServiceRepository;

/**
 * This class is a REST controller that handles HTTP requests related to services.
 * It provides endpoints to retrieve all services and a specific service by its UID.
 * Routes:
 * - GET /services: Retrieve all services
 * - GET /services/{uid}: Retrieve a specific service by its UID
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    ServiceRepository repository;

	@Autowired
	ScriptRepository scriptRepository;

	@GetMapping
	public Collection<ServiceModel> services() {
		return repository.findAll().stream()
			.map(service -> ServiceMapper.toDomain(service))
			.map(service -> ServiceMapper.toModel(service))
			.collect(Collectors.toList());
	}

	@GetMapping("/{uid}")
	public ServiceModel service(@PathVariable String uid) {
		return repository.findAll().stream()
			.map(service -> ServiceMapper.toDomain(service))
			.filter(service -> service.uid().equals(uid))
			.findFirst()
			.map(service -> ServiceMapper.toModel(service))
			.orElse(null);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addService(@RequestBody ServiceModelPost serviceModelPost) {
		System.out.println("Adding service: " + serviceModelPost);
		ServiceEntity serviceEntity = new ServiceEntity(
			serviceModelPost.name(),
			serviceModelPost.port()
		);

		repository.save(serviceEntity);


		System.out.println("Scripts are :");

		Collection<ScriptEntity> scriptsEntities = serviceModelPost.scriptsId().stream()
			.map(scriptId -> scriptRepository.findById(scriptId).orElse(null))
			.filter(scriptEntity -> scriptEntity != null) // Filter out nulls in case the script is not found
			.collect(Collectors.toList());

		scriptsEntities.stream().forEach(scriptEntity -> {
			System.out.println("   - " + scriptEntity.getName() + " (ID: " + scriptEntity.getUid() + ")");
			scriptEntity.setServices(
				// Concatenate the existing services with the new service
				scriptEntity.getServices() == null ?
					Stream.of(serviceEntity).collect(Collectors.toList()) :
					Stream.concat(scriptEntity.getServices().stream(), Stream.of(serviceEntity)).collect(Collectors.toList())
			);
			scriptRepository.save(scriptEntity);
		});

		return ResponseEntity.ok("Service added successfully: " + serviceEntity.getUid());
	}

	@PostMapping("/{uid}/delete")
	public ResponseEntity<?> deleteService(@PathVariable String uid) {
		System.out.println("Deleting service with UID: " + uid);

		ServiceEntity serviceEntity = repository.findById(uid).orElse(null);
		if (serviceEntity != null) {
			repository.deleteById(uid);
			return ResponseEntity.ok("Service deleted successfully.");
		} else {
			return ResponseEntity.status(404).body("Delete operation impossible: Service not found.");
		}

	}

	@PostMapping("/{uid}/update")
	public ResponseEntity<?> updateService(@PathVariable String uid, @RequestBody ServiceModel serviceModel) {
		System.out.println("Updating service with UID: " + uid);

		ServiceEntity serviceEntity = repository.findById(uid).orElse(null);
		if (serviceEntity != null) {
			repository.save(serviceEntity);
			return ResponseEntity.ok("Service updated successfully.");
		} else {
			return ResponseEntity.status(404).body("Update operation impossible: Service not found.");
		}
	}
}
