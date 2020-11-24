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

	public boolean signup(SignupRequest signupRequest) {

		Optional<User> checkUser = userRepository.findByUsername(signupRequest.getUsername());

		if (checkUser.isPresent() || signupRequest == null) {
			return false;
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
			return true;
		}
	}

	private String encodePassword(String password) {

		return passwordEncoder.encode(password);
	}

	public LoginResponse login(LoginRequest loginRequest) {

		LoginResponse loginResponse = new LoginResponse();

		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);

		loginResponse.setAuthenticationToken(jwtProvider.generateToken(authenticate));
		loginResponse.setUsername(loginRequest.getUsername());
		loginResponse.setRole(this.getUserRole(loginRequest.getUsername()));

		return loginResponse;
	}

	private String getUserRole(String username ) {

		User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("No user found" + username));

		return user.getRole();
	}
}
