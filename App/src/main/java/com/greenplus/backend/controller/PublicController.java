package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.ShopCardDetailsResponse;
import com.greenplus.backend.dto.ShopDetailsPublicResponse;
import com.greenplus.backend.service.PublicService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

	@Autowired
	PublicService publicService;

	@GetMapping("/shops")
	public ResponseEntity<List<ShopCardDetailsResponse>> getAllShops() {

		return new ResponseEntity<>(publicService.getAllShops(), HttpStatus.OK);
	}

	@GetMapping("/shopsbyuser/{username}")
	public ResponseEntity<List<ShopCardDetailsResponse>> getShopsByUser(@PathVariable String username) {

		return new ResponseEntity<>(publicService.getShopsByUser(username), HttpStatus.OK);
	}

	@GetMapping("/shopbyshopid/{shopId}")
	public ResponseEntity<ShopDetailsPublicResponse> getShopsByUser(@PathVariable int shopId) {

		return new ResponseEntity<>(publicService.getShopsByShopId(shopId), HttpStatus.OK);
	}
}
