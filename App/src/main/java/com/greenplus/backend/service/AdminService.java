package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.dto.UserDetailsResponse;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	public List<UserDetailsResponse> getAllUsers() {

		List<User> users = userRepository.findAll();

		return users.stream().map(this::mapFromUserToDto).collect(Collectors.toList());
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

}
