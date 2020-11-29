package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.BuyerRequestCreatingRequest;
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

}
