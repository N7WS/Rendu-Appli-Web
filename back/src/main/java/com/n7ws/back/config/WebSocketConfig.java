package com.n7ws.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.n7ws.back.api.WebSocketController;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
		registry.addHandler(socketController(), "/ws")
			.setAllowedOrigins(
				"http://localhost:5173",
				"http://localhost:3000"
			);
	}

	@Bean
	public WebSocketController socketController() {
		return new WebSocketController();
	}
}