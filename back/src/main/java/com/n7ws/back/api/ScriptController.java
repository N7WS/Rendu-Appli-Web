package com.n7ws.back.api;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n7ws.back.entity.ScriptEntity;
import com.n7ws.back.mapper.ScriptMapper;
import com.n7ws.back.model.ScriptModel;
import com.n7ws.back.repository.ScriptRepository;

/**
 * This class is a REST controller that handles HTTP requests related to scripts.
 * It provides endpoints to retrieve all scripts and a specific script by its UID.
 * Routes:
 * - GET /scripts: Retrieve all scripts
 * - GET /scripts/{uid}: Retrieve a specific script by its UID
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/scripts")
public class ScriptController {

	@Autowired
    ScriptRepository repository;

	@GetMapping
	public Collection<ScriptModel> scripts() {
		System.out.println("Retrieving all scripts");
 
		return repository.findAll().stream()
			.map(script -> ScriptMapper.toDomain(script))
            .map(script -> ScriptMapper.toModel(script))
            .collect(Collectors.toList());
	}

	@GetMapping("/{uid}")
	public ScriptModel script(@PathVariable String uid) {
		return repository.findAll().stream()
			.map(script -> ScriptMapper.toDomain(script))
			.filter(script -> script.uid().equals(uid))
			.findFirst()
			.map(script -> ScriptMapper.toModel(script))
			.orElse(null);
	}

	@PostMapping("/add")
	public ResponseEntity<?> addScript(@RequestBody ScriptModel scriptModel) {
		//TODO: Check for duplicate script names ?
		System.out.println("Adding script: " + scriptModel);

		ScriptEntity scriptEntity = ScriptMapper.toEntity(scriptModel);
		return ResponseEntity.ok(repository.save(scriptEntity));
	}

	@PostMapping("/{uid}/delete")
	public ResponseEntity<?> deleteScript(@PathVariable String uid) {
		System.out.println("Deleting script with UID: " + uid);

		ScriptEntity scriptEntity = repository.findById(uid).orElse(null);
		if (scriptEntity != null) {
			repository.deleteById(uid);
			return ResponseEntity.ok("Script deleted successfully");
		} else {
			return ResponseEntity.status(404).body("Script not found");
		}
	}

	@PostMapping("/{uid}/update")
	public ResponseEntity<?> updateScript(@PathVariable String uid, @RequestBody ScriptModel scriptModel) {
		System.out.println("Updating script with UID: " + uid);

		ScriptEntity scriptEntity = repository.findById(uid).orElse(null);
		if (scriptEntity != null) {
			repository.save(scriptEntity);
			return ResponseEntity.ok("Service updated successfully");
		} else {
			return ResponseEntity.status(404).body("Script not found");
		}
	}
}
