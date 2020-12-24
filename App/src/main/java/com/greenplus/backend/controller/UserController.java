package com.greenplus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.service.UserManagementService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserManagementService userManagementService;

	@GetMapping("/{username}")
	public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable String username) {

		return new ResponseEntity<>(userManagementService.getUserDetails(username), HttpStatus.OK);
	}

	@PutMapping("/resetpassword/{username}")
	public Response resetPassword(@PathVariable String username,
			@RequestBody ResetPasswordByUserRequest resetPasswordByUserRequest) {

		return userManagementService.resetPassword(username, resetPasswordByUserRequest);
	}

	@PutMapping("/updateuserdetails/{username}")
	public Response updateUserDetails(@PathVariable String username,
			@RequestBody UserDetailsUpdateRequest userDetailsUpdateRequest) {

		return userManagementService.updateUserDetails(username, userDetailsUpdateRequest);
	}

	@PutMapping("/deactivateaccount/{username}")
	public Response deactivateAccount(@PathVariable String username) {

		return userManagementService.deactivateAccount(username);
	}

}
