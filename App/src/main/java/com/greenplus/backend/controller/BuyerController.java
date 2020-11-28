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
import com.greenplus.backend.dto.SetAccountStatusRequest;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.service.AdminService;
import com.greenplus.backend.service.PublicService;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

	@Autowired
	PublicService publicService;

	@Autowired
	AdminService adminService;

	@GetMapping("/{username}")
	public ResponseEntity<UserDetailsResponse> getFarmerDetails(@PathVariable String username) {

		return new ResponseEntity<>(publicService.getUserDetails(username), HttpStatus.OK);
	}

	@PutMapping("/setaccountstatus")
	public Response setAccountStatus(@RequestBody SetAccountStatusRequest setAccountStatusRequest) {

		return adminService.setAccountStatus(setAccountStatusRequest);

	}

	@PutMapping("/resetpassword")
	public Response resetPassword(@RequestBody ResetPasswordByUserRequest resetPasswordByUserRequest) {

		return publicService.resetPassword(resetPasswordByUserRequest);

	}

	@PutMapping("/updateuserdetails")
	public Response updateUserDetails(@RequestBody UserDetailsUpdateRequest userDetailsUpdateRequest) {

		return publicService.updateUserDetails(userDetailsUpdateRequest);

	}
}
