package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class PublicService {

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Response response;

	public List<ShopDetailsResponse> getAllShops() {

		List<Shop> shops = shopRepository.findByShopStatus(true);

		return shops.stream().map(this::mapFromShopToDto).collect(Collectors.toList());

	}

	private ShopDetailsResponse mapFromShopToDto(Shop shop) {

		ShopDetailsResponse shopDetailsResponse = new ShopDetailsResponse();

		shopDetailsResponse.setShopId(shop.getShopId());
		shopDetailsResponse.setTitle(shop.getTitle());
		shopDetailsResponse.setCategory(shop.getCategory());
		shopDetailsResponse.setSubCategory(shop.getSubCategory());
		shopDetailsResponse.setDescription(shop.getDescription());
		shopDetailsResponse.setQuantity(shop.getQuantity());
		shopDetailsResponse.setPrice(shop.getPrice());
		shopDetailsResponse.setLocation(shop.getLocation());
		shopDetailsResponse.setCreatedDate(shop.getCreatedDate());
		shopDetailsResponse.setCreatedTime(shop.getCreatedTime());
		shopDetailsResponse.setDeliveryTime(shop.getDeliveryTime());
		shopDetailsResponse.setShopStatus(shop.isShopStatus());

		return shopDetailsResponse;
	}

	public Response resetPassword(ResetPasswordByUserRequest resetPasswordByUserRequest) {

		if (validateUser(resetPasswordByUserRequest.getUserUsername(), resetPasswordByUserRequest.getOldPassword())) {

			User user = userRepository.findByUsername(resetPasswordByUserRequest.getUserUsername());

			user.setPassword(passwordEncoder.encode(resetPasswordByUserRequest.getUserNewPassword()));
			userRepository.save(user);

			response.setResponseBody("Password changed sucessfully!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody("User validation failed!");
			response.setResponseStatus(false);

			return response;
		}

	}

	private boolean validateUser(String username, String password) {

		User user = userRepository.findByUsername(username);

		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {

				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}

	}

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

}
