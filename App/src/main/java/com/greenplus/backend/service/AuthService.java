package com.greenplus.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.SignupRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean signup(SignupRequest signupRequest) {

		Optional<User> checkUser = userRepository.findByUsername(signupRequest.getUsername());

		if (checkUser.isPresent()) {
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
}
