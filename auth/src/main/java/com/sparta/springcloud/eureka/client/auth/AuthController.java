package com.sparta.springcloud.eureka.client.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@GetMapping("auth/sign-In")
	public ResponseEntity<?> createAuthToken(@RequestParam String userId) {
		return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(userId)));
	}

	@Data
	@AllArgsConstructor
	static class AuthResponse {
		private String accessToken;
	}
}
