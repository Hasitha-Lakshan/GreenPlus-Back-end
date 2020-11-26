package com.greenplus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.service.FarmerService;

@RestController
@RequestMapping("/api/farmer")
public class FarmerController {

	@Autowired
	private FarmerService farmerService;

	@PostMapping("/shopcreating")
	public Response shopcreating(@RequestBody ShopCreatingRequest shopCreatingRequest) {

		return farmerService.shopCreating(shopCreatingRequest);
	}
}
