package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
