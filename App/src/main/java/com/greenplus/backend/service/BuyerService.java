package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.BuyerRequestCreatingRequest;
import com.greenplus.backend.dto.BuyerRequestDashboardResponse;
import com.greenplus.backend.dto.BuyerRequestDetailsPublicResponse;
import com.greenplus.backend.dto.BuyerRequestDetailsResponse;
import com.greenplus.backend.dto.BuyerRequestUpdateRequest;
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
				buyerRequest.setUnit(buyerRequestCreatingRequest.getUnit());
				buyerRequest.setQuantity(buyerRequestCreatingRequest.getQuantity());
				buyerRequest.setPrice(buyerRequestCreatingRequest.getPrice());
				buyerRequest.setLocation(buyerRequestCreatingRequest.getLocation());
				buyerRequest.setCreatedDate(buyerRequestCreatingRequest.getCreatedDate());
				buyerRequest.setExpectDays(buyerRequestCreatingRequest.getExpectDays());
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

	private BuyerRequestDetailsResponse mapFromBuyerRequestToBuyerRequestDetailsResponseDto(BuyerRequest buyerRequest) {

		BuyerRequestDetailsResponse buyerRequestDetailsResponse = new BuyerRequestDetailsResponse();

		buyerRequestDetailsResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDetailsResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDetailsResponse.setCategory(buyerRequest.getCategory());
		buyerRequestDetailsResponse.setSubCategory(buyerRequest.getSubCategory());
		buyerRequestDetailsResponse.setDescription(buyerRequest.getDescription());
		buyerRequestDetailsResponse.setUnit(buyerRequest.getUnit());
		buyerRequestDetailsResponse.setQuantity(buyerRequest.getQuantity());
		buyerRequestDetailsResponse.setPrice(buyerRequest.getPrice());
		buyerRequestDetailsResponse.setLocation(buyerRequest.getLocation());
		buyerRequestDetailsResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDetailsResponse.setExpectDays(buyerRequest.getExpectDays());
		buyerRequestDetailsResponse.setBuyerRequestStatus(buyerRequest.isBuyerRequestStatus());

		return buyerRequestDetailsResponse;
	}

	private BuyerRequestDetailsPublicResponse mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto(
			BuyerRequest buyerRequest) {

		BuyerRequestDetailsPublicResponse buyerRequestDetailsPublicResponse = new BuyerRequestDetailsPublicResponse();

		buyerRequestDetailsPublicResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDetailsPublicResponse.setUsername(buyerRequest.getUser().getUsername());
		buyerRequestDetailsPublicResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDetailsPublicResponse.setCategory(buyerRequest.getCategory());
		buyerRequestDetailsPublicResponse.setSubCategory(buyerRequest.getSubCategory());
		buyerRequestDetailsPublicResponse.setDescription(buyerRequest.getDescription());
		buyerRequestDetailsPublicResponse.setUnit(buyerRequest.getUnit());
		buyerRequestDetailsPublicResponse.setQuantity(buyerRequest.getQuantity());
		buyerRequestDetailsPublicResponse.setPrice(buyerRequest.getPrice());
		buyerRequestDetailsPublicResponse.setLocation(buyerRequest.getLocation());
		buyerRequestDetailsPublicResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDetailsPublicResponse.setExpectDays(buyerRequest.getExpectDays());

		return buyerRequestDetailsPublicResponse;
	}

	public List<BuyerRequestDetailsPublicResponse> getActiveBuyerRequestsByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.getRole().equals("BUYER")) {

			List<BuyerRequest> buyerRequests = buyerRequestRepository.findByUserAndBuyerRequestStatus(user, true);

			return buyerRequests.stream().map(this::mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto)
					.collect(Collectors.toList());
		} else {
			return null;
		}

	}

	public BuyerRequestDetailsResponse getBuyerRequestByBuyerRequestId(int buyerRequestId) {

		BuyerRequest buyerRequest = buyerRequestRepository.findByBuyerRequestId(buyerRequestId);

		if (buyerRequest != null) {

			return this.mapFromBuyerRequestToBuyerRequestDetailsResponseDto(buyerRequest);

		} else {
			return null;
		}
	}

	private BuyerRequestDashboardResponse mapFromBuyerRequestToBuyerRequestDashboardResponseDto(
			BuyerRequest buyerRequest) {

		BuyerRequestDashboardResponse buyerRequestDashboardResponse = new BuyerRequestDashboardResponse();

		buyerRequestDashboardResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDashboardResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDashboardResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDashboardResponse.setBuyerRequestStatus(buyerRequest.isBuyerRequestStatus());

		return buyerRequestDashboardResponse;
	}

	public List<BuyerRequestDashboardResponse> getAllBuyerRequestByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.getRole().equals("BUYER")) {

			List<BuyerRequest> buyerRequests = buyerRequestRepository.findByUser(user);

			return buyerRequests.stream().map(this::mapFromBuyerRequestToBuyerRequestDashboardResponseDto)
					.collect(Collectors.toList());
		} else {
			return null;
		}
	}

	public Response buyerRequestUpdate(int buyerRequestId, BuyerRequestUpdateRequest buyerRequestUpdateRequest) {

		BuyerRequest buyerRequest = buyerRequestRepository.findByBuyerRequestId(buyerRequestId);

		if (buyerRequest != null) {

			buyerRequest.setTitle(buyerRequestUpdateRequest.getTitle());
			buyerRequest.setCategory(buyerRequestUpdateRequest.getCategory());
			buyerRequest.setSubCategory(buyerRequestUpdateRequest.getSubCategory());
			buyerRequest.setDescription(buyerRequestUpdateRequest.getDescription());
			buyerRequest.setUnit(buyerRequestUpdateRequest.getUnit());
			buyerRequest.setQuantity(buyerRequestUpdateRequest.getQuantity());
			buyerRequest.setPrice(buyerRequestUpdateRequest.getPrice());
			buyerRequest.setLocation(buyerRequestUpdateRequest.getLocation());
			buyerRequest.setExpectDays(buyerRequestUpdateRequest.getExpectDays());
			buyerRequest.setBuyerRequestStatus(buyerRequestUpdateRequest.isBuyerRequestStatus());

			buyerRequestRepository.save(buyerRequest);

			response.setResponseBody("Buyer Request successfully updated!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody("The Buyer Request does not exsit, Buyer Request update failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

}