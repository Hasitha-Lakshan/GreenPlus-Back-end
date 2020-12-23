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
import com.greenplus.backend.dto.BuyerRequestDashboardResponse;
import com.greenplus.backend.dto.BuyerRequestDetailsPublicResponse;
import com.greenplus.backend.dto.BuyerRequestDetailsResponse;
import com.greenplus.backend.dto.BuyerRequestUpdateRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.service.BuyerService;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

	@Autowired
	BuyerService buyerService;

	@PostMapping("/buyerrequestcreationg")
	public Response buyerRequestCreating(@RequestBody BuyerRequestCreatingRequest buyerRequestCreatingRequest) {

		return buyerService.buyerRequestCreating(buyerRequestCreatingRequest);
	}

	@DeleteMapping("/buyerrequestdelete/{buyerRequestId}")
	public Response shopDelete(@PathVariable int buyerRequestId) {

		return buyerService.buyerRequestDelete(buyerRequestId);
	}

	@GetMapping("/activebuyerrequestsbyuser/{username}")
	public ResponseEntity<List<BuyerRequestDetailsPublicResponse>> getActiveBuyerRequestByUser(
			@PathVariable String username) {

		return new ResponseEntity<>(buyerService.getActiveBuyerRequestsByUser(username), HttpStatus.OK);
	}

	@PutMapping("/buyerrequestupdate/{buyerRequestId}")
	public Response buyerRequestUpdate(@PathVariable int buyerRequestId,
			@RequestBody BuyerRequestUpdateRequest buyerRequestUpdateRequest) {

		return buyerService.buyerRequestUpdate(buyerRequestId, buyerRequestUpdateRequest);
	}

	@GetMapping("/buyerrequestsbyuser/{username}")
	public ResponseEntity<List<BuyerRequestDashboardResponse>> getAllBuyerRequestByUser(@PathVariable String username) {

		return new ResponseEntity<>(buyerService.getAllBuyerRequestByUser(username), HttpStatus.OK);
	}

	@GetMapping("/buyerrequestbybuyerrequestid/{buyerRequestId}")
	public ResponseEntity<BuyerRequestDetailsResponse> getBuyerRequestByBuyerRequestId(
			@PathVariable int buyerRequestId) {

		return new ResponseEntity<>(buyerService.getBuyerRequestByBuyerRequestId(buyerRequestId), HttpStatus.OK);
	}
}
