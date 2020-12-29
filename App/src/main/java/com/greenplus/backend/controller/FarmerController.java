package com.greenplus.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.greenplus.backend.dto.BuyerRequestDetailsPublicResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.dto.ShopDashboardResponse;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.ShopUpdateRequest;
import com.greenplus.backend.service.FarmerService;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

	@Autowired
	private FarmerService farmerService;

	@PostMapping("/shopcreating")
	public Response shopcreating(@RequestPart(value = "shopPicture", required = true) MultipartFile shopPicture,
			@RequestPart(value = "shopDetails", required = true) ShopCreatingRequest shopCreatingRequest)
			throws IOException {

		return farmerService.shopCreating(shopPicture, shopCreatingRequest);
	}

	@GetMapping("/shopsbyshopid/{shopId}")
	public ResponseEntity<ShopDetailsResponse> getShopsByShopId(@PathVariable int shopId) {

		return new ResponseEntity<>(farmerService.getShopsByShopId(shopId), HttpStatus.OK);
	}

	@GetMapping("/shopsbyuser/{username}")
	public ResponseEntity<List<ShopDashboardResponse>> getAllAShopsByUser(@PathVariable String username) {

		return new ResponseEntity<>(farmerService.getAllAShopsByUser(username), HttpStatus.OK);
	}

	@PutMapping("/shopupdate/{shopId}")
	public Response shopUpdate(@PathVariable int shopId,
			@RequestPart(value = "shopPicture", required = false) MultipartFile shopPicture,
			@RequestPart(value = "shopDetails", required = true) ShopUpdateRequest shopUpdateRequest) throws IOException {
		
		return farmerService.shopUpdate(shopId, shopUpdateRequest, shopPicture);
	}

	@DeleteMapping("/shopdelete/{shopId}")
	public Response shopDelete(@PathVariable int shopId) {

		return farmerService.shopDelete(shopId);
	}

	@GetMapping("/buyerrequests")
	public ResponseEntity<List<BuyerRequestDetailsPublicResponse>> getAllBuyerRequests() {

		return new ResponseEntity<>(farmerService.getAllBuyerRequests(), HttpStatus.OK);
	}

}
