package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.LoginRequest;
import com.greenplus.backend.dto.LoginResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.SignupRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.security.JwtProvider;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private Response response;

	public Response signup(SignupRequest signupRequest) {

		if (signupRequest != null) {

			User checkUserByUsername = userRepository.findByUsername(signupRequest.getUsername());
			User checkUserByEmail = userRepository.findByEmail(signupRequest.getEmail());
			User checkUserByMobileNumber = userRepository.findByMobileNumber(signupRequest.getMobileNumber());

			if (checkUserByUsername != null && checkUserByEmail != null && checkUserByMobileNumber != null) {
				response.setResponseBody("Given username, email and mobile number maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else if (checkUserByUsername != null && checkUserByEmail != null) {

				response.setResponseBody("Given username and email maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else if (checkUserByUsername != null && checkUserByMobileNumber != null) {

				response.setResponseBody("Given username and mobile number maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else if (checkUserByEmail != null && checkUserByMobileNumber != null) {

				response.setResponseBody("Given email and mobile number maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;
			}

			else if (checkUserByUsername != null) {

				response.setResponseBody("Given username maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else if (checkUserByEmail != null) {

				response.setResponseBody("Given email maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else if (checkUserByMobileNumber != null) {

				response.setResponseBody("Given mobile number maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;

			} else {

				User user = new User();

				user.setUsername(signupRequest.getUsername());
				user.setFirstName(signupRequest.getFirstName());
				user.setLastName(signupRequest.getLastName());
				user.setRole(signupRequest.getRole());
				user.setEmail(signupRequest.getEmail());
				user.setMobileNumber(signupRequest.getMobileNumber());
				user.setAddressLine1(signupRequest.getAddressLine1());
				user.setAddressLine2(signupRequest.getAddressLine2());
				user.setAddressLine3(signupRequest.getAddressLine3());

				if (signupRequest.getRole().equals("ADMIN")) {
					user.setAccountStatus(false);
				}

				else {
					user.setAccountStatus(true);
				}

				if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {

					response.setResponseBody("Password and Confirm Password does not match, Registration failed!");
					response.setResponseStatus(false);

					return response;

				} else {
					user.setPassword(encodePassword(signupRequest.getPassword()));
					userRepository.save(user);

					response.setResponseBody("Registration Completed!");
					response.setResponseStatus(true);

					return response;
				}
			}
		}

		else {
			response.setResponseBody("Registration Failed!");
			response.setResponseStatus(false);

			return response;
		}

	}

	private String encodePassword(String password) {

		return passwordEncoder.encode(password);
	}

	public LoginResponse login(LoginRequest loginRequest) {

		LoginResponse loginResponse = new LoginResponse();

		this.reActivateUserAccount(loginRequest.getUsername());

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		loginResponse.setAuthenticationtoken(jwtProvider.generateToken(authenticate));
		loginResponse.setUsername(loginRequest.getUsername());
		loginResponse.setRole(this.getUserRole(loginRequest.getUsername()));

		return loginResponse;
	}

	private String getUserRole(String username) {

		User user = userRepository.findByUsername(username);

		return user.getRole();
	}

	private void reActivateUserAccount(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			if (user.getRole().equals("FARMER")
					|| user.getRole().equals("BUYER") && (user.isAccountStatus() == false)) {
				user.setAccountStatus(true);
				userRepository.save(user);
			}
		}
	}
}
