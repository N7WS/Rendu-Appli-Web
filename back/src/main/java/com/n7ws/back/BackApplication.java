package com.n7ws.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is the entry point of the N7WS backend application.
 * It is responsible for starting the Spring Boot application.
 * The application is configured to run on port 8080 by default.
 * The main method is the entry point of the application.
 *
 * @version 1.0
 * @see SpringApplication
 */
@SpringBootApplication
public class BackApplication {

	/**
	 * The BackApplication class only has a default constructor.
	 */
	public BackApplication() {
		// empty constructor
	}

	/**
	 * The main method is the entry point of the N7WS backend application.
	 * It starts the Spring Boot application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
