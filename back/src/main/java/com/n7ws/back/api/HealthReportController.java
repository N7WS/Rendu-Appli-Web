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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.n7ws.back.entity.HealthReportEntity;
import com.n7ws.back.mapper.HealthReportMapper;
import com.n7ws.back.model.HealthReportModel;
import com.n7ws.back.repository.HealthReportRepository;
import com.n7ws.back.service.TaskService;

/**
 * This class is a REST controller that handles HTTP requests related to health information.
 * It provides endpoints to retrieve all health information and a specific health information by its UID.
 * Routes:
 * - GET /healthInfos: Retrieve all health information
 * - GET /healthInfos/{uid}: Retrieve a specific health information by its UID
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/healthReports")
public class HealthReportController {

    @Autowired
    HealthReportRepository repository;

    @Autowired
    private WebSocketController socketController;

	@Autowired
    private TaskService taskService;

	@GetMapping
	public Collection<HealthReportModel> healthInfos() {
		return repository.findAll().stream()
			.map(healthInfo -> HealthReportMapper.toDomain(healthInfo))
			.map(healthInfo -> HealthReportMapper.toModel(healthInfo))
			.collect(Collectors.toList());
	}

	@GetMapping("/{deviceName}")
	public Collection<HealthReportModel> healthInfo(@PathVariable String deviceName) {
		return repository.findAllByDeviceName(deviceName)
			.stream()
			.map(healthInfo -> HealthReportMapper.toDomain(healthInfo))
			.map(healthInfo -> HealthReportMapper.toModel(healthInfo))
			.collect(Collectors.toList());
	}

	@PostMapping("/{deviceName}/add")
	public ResponseEntity<?> addHealthInfo(@PathVariable String deviceName, @RequestBody HealthReportModel healthReportModel) {
		HealthReportEntity healthReportEntity = HealthReportMapper.toEntity(healthReportModel);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			// Envoi d'un message WebSocket à tous les clients connectés
			Collection<HealthReportModel> healthReportModels = repository.findAllByDeviceName(healthReportEntity.getDeviceName())
				.stream()
				.map(healthInfo -> HealthReportMapper.toDomain(healthInfo))
				.map(healthInfo -> HealthReportMapper.toModel(healthInfo))
				.collect(Collectors.toList());

			socketController.broadcast(
				healthReportModels.stream()
					.map(hr -> {
						try {
							return objectMapper.writeValueAsString(hr);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					})
					.reduce((fst, snd) -> snd)
					.orElse("")
			);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean sendTask = taskService.hasTasks(deviceName);
		try {
			repository.save(healthReportEntity);
		} catch (Exception e) {
			sendTask = false;
		}
		return ResponseEntity.ok(sendTask);
	}
}
