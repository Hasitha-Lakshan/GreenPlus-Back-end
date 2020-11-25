package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.ResetPasswordByAdminRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SetAccountStatusRequest;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.service.AdminService;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

	@Autowired
	AdminService adminService;

	@GetMapping("/users")
	public ResponseEntity<List<UserDetailsResponse>> getAllUsers() {

		return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/admins")
	public ResponseEntity<List<UserDetailsResponse>> getAllAdmins() {

		return new ResponseEntity<>(adminService.getAllAdmins(), HttpStatus.OK);
	}

	@GetMapping("/farmers")
	public ResponseEntity<List<UserDetailsResponse>> getAllFarmers() {

		return new ResponseEntity<>(adminService.getAllFarmers(), HttpStatus.OK);
	}

	@GetMapping("/buyers")
	public ResponseEntity<List<UserDetailsResponse>> getAllBuyers() {

		return new ResponseEntity<>(adminService.getAllBuyers(), HttpStatus.OK);
	}

	@PutMapping("/setaccountstatus")
	public Response setAccountStatus(@RequestBody SetAccountStatusRequest setAccountStatusRequest) {

		return adminService.setAccountStatus(setAccountStatusRequest);

	}
	
	@PutMapping("/resetpassword")
	public Response resetPassword(@RequestBody ResetPasswordByAdminRequest resetPasswordByAdminRequest) {

		return adminService.resetPassword(resetPasswordByAdminRequest);

	}
}
