package com.greenplus.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.ProfilePictureResponse;
import com.greenplus.backend.dto.ShopCardDetailsResponse;
import com.greenplus.backend.dto.ShopDetailsPublicResponse;
import com.greenplus.backend.dto.UserDetailsPublicResponse;
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

		return new ResponseEntity<>(publicService.getActiveShopsByUser(username), HttpStatus.OK);
	}

	@GetMapping("/shopbyshopid/{shopId}")
	public ResponseEntity<ShopDetailsPublicResponse> getShopByShopId(@PathVariable int shopId) {

		return new ResponseEntity<>(publicService.getShopsByShopId(shopId), HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<UserDetailsPublicResponse> getUserDetailsPublic(@PathVariable String username) {

		return new ResponseEntity<>(publicService.getUserDetailsPublic(username), HttpStatus.OK);
	}

	@GetMapping("/user/profilepicture/{username}")
	public ResponseEntity<ProfilePictureResponse> getProfilePicture(@PathVariable String username) {

		return new ResponseEntity<>(publicService.getProfilePicture(username), HttpStatus.OK);
	}
}
