package com.greenplus.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private String authenticationtoken;
	private String username;
	private String role;

	public String getAuthenticationtoken() {
		return authenticationtoken;
	}

	public void setAuthenticationtoken(String authenticationtoken) {
		this.authenticationtoken = authenticationtoken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}