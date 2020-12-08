package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.BuyerRequestCreatingRequest;
import com.greenplus.backend.dto.BuyerRequestDetailsResponse;
import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SetAccountStatusRequest;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.service.AdminService;
import com.greenplus.backend.service.BuyerService;
import com.greenplus.backend.service.PublicService;
import com.greenplus.backend.service.UserManagementService;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

	@Autowired
	PublicService publicService;

	@Autowired
	AdminService adminService;

	@Autowired
	BuyerService buyerService;
	
	@Autowired
	UserManagementService userManagementService;

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

		return userManagementService.resetPassword(resetPasswordByUserRequest);

	}

	@PutMapping("/updateuserdetails")
	public Response updateUserDetails(@RequestBody UserDetailsUpdateRequest userDetailsUpdateRequest) {

		return userManagementService.updateUserDetails(userDetailsUpdateRequest);

	}

	@PostMapping("/buyerrequestcreationg")
	public Response buyerRequestCreating(@RequestBody BuyerRequestCreatingRequest buyerRequestCreatingRequest) {

		return buyerService.buyerRequestCreating(buyerRequestCreatingRequest);
	}

	@DeleteMapping("/buyerrequestdelete/{buyerRequestId}")
	public Response shopDelete(@PathVariable int buyerRequestId) {

		return buyerService.buyerRequestDelete(buyerRequestId);
	}

	@GetMapping("/buyerrequestsbyuser/{username}")
	public ResponseEntity<List<BuyerRequestDetailsResponse>> getBuyerRequestByUser(@PathVariable String username) {

		return new ResponseEntity<>(buyerService.getBuyerRequestsByUser(username), HttpStatus.OK);
	}

	@GetMapping("/buyerrequestbybuyerrequestid/{buyerRequestId}")
	public ResponseEntity<BuyerRequestDetailsResponse> getBuyerRequestByBuyerRequestId(
			@PathVariable int buyerRequestId) {

		return new ResponseEntity<>(buyerService.getBuyerRequestByBuyerRequestId(buyerRequestId), HttpStatus.OK);
	}
}
