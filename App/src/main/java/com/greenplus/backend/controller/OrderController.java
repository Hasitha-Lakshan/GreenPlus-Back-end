package com.greenplus.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderRequirementResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/orderrequirement/")
	public ResponseEntity<OrderRequirementResponse> orderRequirementDetails(@RequestParam(value = "shopId") int shopId,
			@RequestParam(value = "buyerUsername") String buyerUsername) {

		return new ResponseEntity<>(orderService.orderRequirementDetails(shopId, buyerUsername), HttpStatus.OK);
	}
	
	@PostMapping("/ordercreationg/")
	public ResponseEntity<Response> createOrder(@RequestBody OrderCreatingRequest orderCreatingRequest) {

		return new ResponseEntity<>(orderService.createOrder(orderCreatingRequest), HttpStatus.OK);
	}
}
