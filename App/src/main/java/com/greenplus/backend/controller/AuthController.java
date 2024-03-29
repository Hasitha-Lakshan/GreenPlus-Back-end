package com.greenplus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.LoginRequest;
import com.greenplus.backend.dto.LoginResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SignupRequest;
import com.greenplus.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public Response signup(@RequestBody SignupRequest signupRequest) {

		return authService.signup(signupRequest);
	}

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {

		return authService.login(loginRequest);
	}
}
