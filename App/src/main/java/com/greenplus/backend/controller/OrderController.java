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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderDashboardResponse;
import com.greenplus.backend.dto.OrderDetailsResponse;
import com.greenplus.backend.dto.OrderRequirementResponse;
import com.greenplus.backend.dto.OrderStatusChangeRequest;
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

	@PostMapping("/orders/{username}")
	public ResponseEntity<List<OrderDashboardResponse>> getOrdersByUser(@PathVariable String username,
			@RequestBody String orderStatus) {

		return new ResponseEntity<>(orderService.getOrdersByUser(username, orderStatus), HttpStatus.OK);
	}

	@PostMapping("/ordersbyfarmer/{username}")
	public ResponseEntity<List<OrderDashboardResponse>> getOrdersByFarmer(@PathVariable String username,
			@RequestBody String orderStatus) {

		return new ResponseEntity<>(orderService.getOrdersByFarmer(username, orderStatus), HttpStatus.OK);
	}

	@GetMapping("/orderdetails/{orderId}")
	public ResponseEntity<OrderDetailsResponse> getOrderDetailsByOrderId(@PathVariable int orderId) {

		return new ResponseEntity<>(orderService.getOrderDetailsByOrderId(orderId), HttpStatus.OK);
	}

	@PutMapping("/changeorderstatus/")
	public ResponseEntity<Response> changeOrderStatusByOrderId(@RequestBody OrderStatusChangeRequest orderStatusChangeRequest) {

		return new ResponseEntity<>(orderService.changeOrderStatusByOrderId(orderStatusChangeRequest),
				HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteorder/{orderId}")
	public Response orderDelete(@PathVariable int orderId) {

		return orderService.orderDelete(orderId);
	}
}
