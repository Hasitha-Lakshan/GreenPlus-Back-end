package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SetAccountStatusRequest;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Response response;

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

	public List<UserDetailsResponse> getAllUsers() {

		List<User> users = userRepository.findAll();

		return users.stream().map(this::mapFromUserToDto).collect(Collectors.toList());
	}

	public List<UserDetailsResponse> getAllAdmins() {

		List<User> users = userRepository.findByRole("ADMIN");

		return users.stream().map(this::mapFromUserToDto).collect(Collectors.toList());
	}

	public List<UserDetailsResponse> getAllFarmers() {

		List<User> users = userRepository.findByRole("FARMER");

		return users.stream().map(this::mapFromUserToDto).collect(Collectors.toList());
	}

	public List<UserDetailsResponse> getAllBuyers() {

		List<User> users = userRepository.findByRole("BUYER");

		return users.stream().map(this::mapFromUserToDto).collect(Collectors.toList());
	}

	public Response setAccountStatus(SetAccountStatusRequest setAccountStatusRequest) {

		User user = userRepository.findByUsername(setAccountStatusRequest.getUsername());

		if (user != null) {

			user.setAccountStatus(setAccountStatusRequest.isAccountStatus());
			userRepository.save(user);

			response.setResponseBody("Account Status Successfully Changed!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody(
					"Account Status not Changed!, username:" + setAccountStatusRequest.getUsername() + " doest not exist");
			response.setResponseStatus(false);

			return response;
		}

	}
}
