package com.greenplus.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class UserManagementService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Response response;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private BuyerRequestRepository buyerRequestRepository;

	public UserDetailsResponse getUserDetails(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.isAccountStatus() == true) {

			return this.mapFromUserToDto(user);
		} else {

			return null;
		}
	}

	private UserDetailsResponse mapFromUserToDto(User user) {

		UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

		userDetailsResponse.setUserId(user.getUserId());
		userDetailsResponse.setFirstName(user.getFirstName());
		userDetailsResponse.setLastName(user.getLastName());
		userDetailsResponse.setUsername(user.getUsername());
		userDetailsResponse.setAccountStatus(user.isAccountStatus());
		userDetailsResponse.setRole(user.getRole());
		userDetailsResponse.setMobileNumber(user.getMobileNumber());
		userDetailsResponse.setEmail(user.getEmail());
		userDetailsResponse.setAddressLine1(user.getAddressLine1());
		userDetailsResponse.setAddressLine2(user.getAddressLine2());
		userDetailsResponse.setAddressLine3(user.getAddressLine3());

		return userDetailsResponse;
	}

	public Response resetPassword(String username, ResetPasswordByUserRequest resetPasswordByUserRequest) {

		if (validateUser(username, resetPasswordByUserRequest.getOldPassword())) {

			if (resetPasswordByUserRequest.getUserNewPassword()
					.equals(resetPasswordByUserRequest.getUserNewconfirmPassword())) {

				User user = userRepository.findByUsername(username);

				user.setPassword(passwordEncoder.encode(resetPasswordByUserRequest.getUserNewPassword()));
				userRepository.save(user);

				response.setResponseBody("Password changed sucessfully!");
				response.setResponseStatus(true);

				return response;
			} else {

				response.setResponseBody(
						"New Password and New Confirm Password did not match, Password changed failed!");
				response.setResponseStatus(false);

				return response;
			}

		} else {

			response.setResponseBody("User validation failed!");
			response.setResponseStatus(false);

			return response;
		}

	}

	private boolean validateUser(String username, String oldPassword) {

		User user = userRepository.findByUsername(username);

		if (user != null) {
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {

				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public Response updateUserDetails(UserDetailsUpdateRequest userDetailsUpdateRequest) {

		if (validateUser(userDetailsUpdateRequest.getUsername(), userDetailsUpdateRequest.getPassword())) {

			User user = userRepository.findByUsername(userDetailsUpdateRequest.getUsername());

			user.setFirstName(userDetailsUpdateRequest.getFirstName());
			user.setLastName(userDetailsUpdateRequest.getLastName());
			user.setAddressLine1(userDetailsUpdateRequest.getAddressLine1());
			user.setAddressLine2(userDetailsUpdateRequest.getAddressLine2());
			user.setAddressLine3(userDetailsUpdateRequest.getAddressLine3());

			userRepository.save(user);

			response.setResponseBody("User details updated successfully!");
			response.setResponseStatus(true);

			return response;
		}

		else {

			response.setResponseBody("User validation failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	public Response deactivateAccount(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			user.setAccountStatus(false);
			userRepository.save(user);

			if (user.getRole().equals("FARMER")) {

				List<Shop> shops = shopRepository.findByShopStatus(true);

				for (Shop shop : shops) {
					shop.setShopStatus(false);
					shopRepository.save(shop);
				}
			}

			if (user.getRole().equals("BUYER")) {

				List<BuyerRequest> buyerRequests = buyerRequestRepository.findByBuyerRequestStatus(true);

				for (BuyerRequest buyerRequest : buyerRequests) {
					buyerRequest.setBuyerRequestStatus(false);
					buyerRequestRepository.save(buyerRequest);
				}
			}

			response.setResponseBody("Account Successfully Deactivated!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody("Account Deactivate Failed!, username:" + username + " doest not exist");
			response.setResponseStatus(false);

			return response;
		}

	}
}
