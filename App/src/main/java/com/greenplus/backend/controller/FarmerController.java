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

import com.greenplus.backend.dto.BuyerRequestDetailsResponse;
import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SetAccountStatusRequest;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.ShopUpdateRequest;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.service.AdminService;
import com.greenplus.backend.service.BuyerService;
import com.greenplus.backend.service.FarmerService;
import com.greenplus.backend.service.PublicService;
import com.greenplus.backend.service.UserManagementService;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

	@Autowired
	private FarmerService farmerService;

	@Autowired
	AdminService adminService;

	@Autowired
	PublicService publicService;

	@Autowired
	BuyerService buyerService;
	
	@Autowired
	UserManagementService userManagementService;

	@PostMapping("/shopcreating")
	public Response shopcreating(@RequestBody ShopCreatingRequest shopCreatingRequest) {

		return farmerService.shopCreating(shopCreatingRequest);
	}

	@GetMapping("/shopsbyuser/{username}")
	public ResponseEntity<List<ShopDetailsResponse>> getShopsByUser(@PathVariable String username) {

		return new ResponseEntity<>(farmerService.getShopsByUser(username), HttpStatus.OK);
	}

	@GetMapping("/shopsbyshopid/{shopId}")
	public ResponseEntity<ShopDetailsResponse> getShopsByUser(@PathVariable int shopId) {

		return new ResponseEntity<>(farmerService.getShopsByShopId(shopId), HttpStatus.OK);
	}

	@PutMapping("/shopupdate/{shopId}")
	public Response shopUpdate(@PathVariable int shopId, @RequestBody ShopUpdateRequest shopUpdateRequest) {

		return farmerService.shopUpdate(shopId, shopUpdateRequest);
	}

	@DeleteMapping("/shopdelete/{shopId}")
	public Response shopDelete(@PathVariable int shopId) {

		return farmerService.shopDelete(shopId);
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDetailsResponse> getFarmerDetails(@PathVariable String username) {

		return new ResponseEntity<>(userManagementService.getUserDetails(username), HttpStatus.OK);
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

	@GetMapping("/buyerrequests")
	public ResponseEntity<List<BuyerRequestDetailsResponse>> getAllBuyerRequests() {

		return new ResponseEntity<>(buyerService.getAllBuyerRequests(), HttpStatus.OK);
	}
}
