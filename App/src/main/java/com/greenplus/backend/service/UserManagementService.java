package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;

@Service
public class UserManagementService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Response response;

	@Autowired
	private UserRepository userRepository;

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
