package com.greenplus.backend.unit_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.greenplus.backend.dto.SignupRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.service.AuthService;

@SpringBootTest
public class AuthServiceTest {

	@Autowired
	AuthService authService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void signupTest() {

		SignupRequest signupRequest = new SignupRequest();

		signupRequest.setUsername("test_username");
		signupRequest.setFirstName("test_firstName");
		signupRequest.setLastName("test_lastName");
		signupRequest.setRole("ADMIN");
		signupRequest.setPassword("test_password");
		signupRequest.setConfirmPassword("test_password");
		signupRequest.setMobileNumber(0771234567);
		signupRequest.setEmail("test@email.com");
		signupRequest.setAddressLine1("test_addressLine1");
		signupRequest.setAddressLine2("test_addressLine2");
		signupRequest.setAddressLine3("test_addressLine3");

		User checkUserByUsername = null;
		User checkUserByEmail = null;
		User checkUserByMobileNumber = null;

		when(userRepository.findByUsername(signupRequest.getUsername())).thenReturn(checkUserByUsername);
		when(userRepository.findByEmail(signupRequest.getEmail())).thenReturn(checkUserByEmail);
		when(userRepository.findByMobileNumber(signupRequest.getMobileNumber())).thenReturn(checkUserByMobileNumber);

		assertEquals(true, authService.signup(signupRequest).isResponseStatus());
	}

}
