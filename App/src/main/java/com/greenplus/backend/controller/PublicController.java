package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.service.FarmerService;
import com.greenplus.backend.service.PublicService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

	@Autowired
	PublicService publicService;
	
	@Autowired
	private FarmerService farmerService;

	@GetMapping("/shops")
	public ResponseEntity<List<ShopDetailsResponse>> getAllShops() {

		return new ResponseEntity<>(publicService.getAllShops(), HttpStatus.OK);
	}
	
	@GetMapping("/shopsbyuser/{username}")
	public ResponseEntity<List<ShopDetailsResponse>> getShopsByUser(@PathVariable String username) {

		return new ResponseEntity<>(farmerService.getShopsByUser(username), HttpStatus.OK);
	}

	@GetMapping("/shopsbyshopid/{shopId}")
	public ResponseEntity<ShopDetailsResponse> getShopsByUser(@PathVariable int shopId) {

		return new ResponseEntity<>(farmerService.getShopsByShopId(shopId), HttpStatus.OK);
	}
}
