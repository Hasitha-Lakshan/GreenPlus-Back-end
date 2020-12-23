package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.UserDetailsResponse;
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

			if (resetPasswordByUserRequest.getUserNewPassword() == resetPasswordByUserRequest
					.getUserNewconfirmPassword()) {

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
}
