package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.BuyerRequestCreatingRequest;
import com.greenplus.backend.dto.BuyerRequestDetailsResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class BuyerService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Response response;

	@Autowired
	private BuyerRequestRepository buyerRequestRepository;

	public Response buyerRequestCreating(BuyerRequestCreatingRequest buyerRequestCreatingRequest) {

		if (buyerRequestCreatingRequest != null) {

			User user = userRepository.findByUsername(buyerRequestCreatingRequest.getUsername());

			if (user == null) {

				response.setResponseBody("The user not found, Buyer request creating is failed!");
				response.setResponseStatus(false);

				return response;
			}

			else if (user != null && user.getRole().equals("BUYER")) {

				BuyerRequest buyerRequest = new BuyerRequest();

				buyerRequest.setTitle(buyerRequestCreatingRequest.getTitle());
				buyerRequest.setDescription(buyerRequestCreatingRequest.getDescription());
				buyerRequest.setCategory(buyerRequestCreatingRequest.getCategory());
				buyerRequest.setSubCategory(buyerRequestCreatingRequest.getSubCategory());
				buyerRequest.setQuantity(buyerRequestCreatingRequest.getQuantity());
				buyerRequest.setPrice(buyerRequestCreatingRequest.getPrice());
				buyerRequest.setLocation(buyerRequestCreatingRequest.getLocation());
				buyerRequest.setCreatedDate(buyerRequestCreatingRequest.getCreatedDate());
				buyerRequest.setCreatedTime(buyerRequestCreatingRequest.getCreatedTime());
				buyerRequest.setDeliveryTime(buyerRequestCreatingRequest.getDeliveryTime());
				buyerRequest.setBuyerRequestStatus(true);
				buyerRequest.setUser(user);

				buyerRequestRepository.save(buyerRequest);

				response.setResponseBody("Buyer request created successfully!");
				response.setResponseStatus(true);

				return response;
			} else {
				response.setResponseBody("Only buyers are allowed to create shops!");
				response.setResponseStatus(false);

				return response;
			}

		} else {

			response.setResponseBody("Buyer request creating failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	@Transactional
	public Response buyerRequestDelete(int buyerRequestId) {

		BuyerRequest buyerRequest = buyerRequestRepository.findByBuyerRequestId(buyerRequestId);

		if (buyerRequest != null) {

			buyerRequestRepository.deleteByBuyerRequestId(buyerRequestId);

			response.setResponseBody("Buyer request successfully deleted!");
			response.setResponseStatus(true);

			return response;
		}

		response.setResponseBody("The Buyer request does not exsit, Buyer request delete failed!");
		response.setResponseStatus(false);

		return response;

	}

	private BuyerRequestDetailsResponse mapFromBuyerRequestToDto(BuyerRequest buyerRequest) {

		BuyerRequestDetailsResponse buyerRequestDetailsResponse = new BuyerRequestDetailsResponse();

		buyerRequestDetailsResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDetailsResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDetailsResponse.setCategory(buyerRequest.getCategory());
		buyerRequestDetailsResponse.setSubCategory(buyerRequest.getSubCategory());
		buyerRequestDetailsResponse.setDescription(buyerRequest.getDescription());
		buyerRequestDetailsResponse.setQuantity(buyerRequest.getQuantity());
		buyerRequestDetailsResponse.setPrice(buyerRequest.getPrice());
		buyerRequestDetailsResponse.setLocation(buyerRequest.getLocation());
		buyerRequestDetailsResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDetailsResponse.setCreatedTime(buyerRequest.getCreatedTime());
		buyerRequestDetailsResponse.setDeliveryTime(buyerRequest.getDeliveryTime());
		buyerRequestDetailsResponse.setBuyerRequestStatus(buyerRequest.isBuyerRequestStatus());

		return buyerRequestDetailsResponse;
	}

	public List<BuyerRequestDetailsResponse> getAllBuyerRequests() {

		List<BuyerRequest> buyerRequests = buyerRequestRepository.findByBuyerRequestStatus(true);

		return buyerRequests.stream().map(this::mapFromBuyerRequestToDto).collect(Collectors.toList());

	}

}
