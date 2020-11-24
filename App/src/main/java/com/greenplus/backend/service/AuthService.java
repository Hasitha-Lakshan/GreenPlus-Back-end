package com.greenplus.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.LoginResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.security.JwtProvider;
import com.greenplus.backend.dto.LoginRequest;
import com.greenplus.backend.dto.SignupRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;

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

			Optional<User> checkUserByUsername = userRepository.findByUsername(signupRequest.getUsername());
			Optional<User> checkUserByEmail = userRepository.findByEmail(signupRequest.getEmail());
			Optional<User> checkUserByMobileNumber = userRepository.findByMobileNumber(signupRequest.getMobileNumber());

			if (checkUserByUsername.isPresent() || checkUserByEmail.isPresent()
					|| checkUserByMobileNumber.isPresent()) {
				response.setResponseBody("Given username, email or mobile number maybe exist, Registration failed!");
				response.setResponseStatus(false);

				return response;
			}

			else {

				User user = new User();

				user.setUsername(signupRequest.getUsername());
				user.setFirstName(signupRequest.getFirstName());
				user.setLastName(signupRequest.getLastName());
				user.setPassword(encodePassword(signupRequest.getPassword()));
				user.setRole(signupRequest.getRole());
				user.setEmail(signupRequest.getEmail());
				user.setMobileNumber(signupRequest.getMobileNumber());
				user.setAddressLine1(signupRequest.getAddressLine1());
				user.setAddressLine2(signupRequest.getAddressLine2());
				user.setAddressLine3(signupRequest.getAddressLine3());

				userRepository.save(user);

				response.setResponseBody("Registration Completed!");
				response.setResponseStatus(true);

				return response;
			}

		}

		else {
			return null;
		}

	}

	private String encodePassword(String password) {

		return passwordEncoder.encode(password);
	}

	public LoginResponse login(LoginRequest loginRequest) {

		LoginResponse loginResponse = new LoginResponse();

		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		loginResponse.setAuthenticationToken(jwtProvider.generateToken(authenticate));
		loginResponse.setUsername(loginRequest.getUsername());
		loginResponse.setRole(this.getUserRole(loginRequest.getUsername()));

		return loginResponse;
	}

	private String getUserRole(String username) {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found" + username));

		return user.getRole();
	}
}
