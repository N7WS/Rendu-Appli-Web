package com.n7ws.back.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n7ws.back.config.JwtUtils;
import com.n7ws.back.entity.UserEntity;
import com.n7ws.back.mapper.UserMapper;
import com.n7ws.back.model.UserModel;
import com.n7ws.back.repository.UserRepository;

/**
 * This class is a REST controller that handles HTTP requests related to users.
 * It provides endpoints to retrieve all users and a specific user by its UID.
 * Routes:
 * - GET /users: Retrieve all users
 * - GET /users/{uid}: Retrieve a specific user by its UID
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

	//private final ?
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	AuthenticationManager authentificationManager;


	@GetMapping
	public Collection<UserModel> users() {
		return repository.findAll().stream()
			.map(user -> UserMapper.toDomain(user))
			.map(user -> UserMapper.toModel(user))
			.collect(Collectors.toList());
	}

	@GetMapping("/{uid}")
	public UserModel user(@PathVariable String uid) {
		return repository.findById(uid)
			.map(UserMapper::toDomain)
			.map(UserMapper::toModel)
			.orElse(null);
	}

	@GetMapping("/me")
	public UserModel user() {
		// Récupération de l'utilisateur authentifié
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			String email = authentication.getName();
			System.out.println("Récupération de l'utilisateur authentifié: " + email);
			UserEntity userEntity = repository.findByEmail(email);
			if (userEntity != null) {
				return UserMapper.toModel(UserMapper.toDomain(userEntity));
			}
		}
		return null; // Si l'utilisateur n'est pas authentifié ou n'existe pas
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserModel user) {
		System.out.println("Registering user: " + user.email());

		if (repository.findByEmail(UserMapper.toEntity(user).getEmail()) != null) {
			return ResponseEntity.badRequest().body("Username already exists");
		}

		// Si on a pas déjà un utilisateur avec le même uid, on peut l'enregistrer
		UserEntity userEntity = UserMapper.toEntity(user);
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword())); // Hash du mot de passe
		return ResponseEntity.ok(repository.save(userEntity)); // Enregistrement de l'utilisateur
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserModel user) {
		System.out.println("Logging in user: " + user.email());

		// User entity
		UserEntity userEntity = repository.findByEmail(user.email());

		// On vérifie si l'utilisateur existe
		if (userEntity == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email ou password");
		}

		System.out.println("Starting authentication ...");

		try {
			System.out.println("Building authentication token ...");
			AbstractAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				userEntity.getUid(),
				user.password()
				// userEntity.getPassword()
			);

			System.out.println("Authenticating user ...");
			System.out.println(authentificationManager);
			System.out.println(token);
			Authentication authentication = authentificationManager.authenticate(token);

			if (authentication.isAuthenticated()) {
				System.out.println("Authentication successful: " + authentication.isAuthenticated());
				// Si l'utilisateur est authentifié, on génère un token JWT

				String jwtToken = jwtUtils.generateToken(userEntity.getUid());
				Map<String, Object> authData = new HashMap<>();
				authData.put("token", jwtToken);
				authData.put("type", "Bearer");

				System.out.println("Token is : " + authData);
				// On retourne le token JWT dans la réponse comme un cookie sécurisé
				HttpCookie cookie = ResponseCookie.from("jwt", jwtToken)
					.httpOnly(true)
					.secure(false) // Passe à true en production (HTTPS)
					.sameSite("Lax")
					.maxAge(7 * 24 * 60 * 60) // 7 jours
					.path("/")
					.build();

				System.out.println("Cookie created: " + cookie.toString());

				return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, cookie.toString())
					.body("{\"message\": \"Login successful\"}");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email ou password");
			}
		} catch (AuthenticationException e) {
			System.out.println("Authentication failed: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
		}
	}

}
