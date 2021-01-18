package com.greenplus.backend.unit_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.model.ProfilePicture;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.ProfilePictureRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.service.UserManagementService;

@SpringBootTest
public class UserManagementServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ShopRepository shopRepository;

	@MockBean
	private BuyerRequestRepository buyerRequestRepository;

	@MockBean
	private ProfilePictureRepository profilePictureRepository;

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void getUserDetailsTest() {

		String username = "test_username";

		User user = new User();

		user.setAccountStatus(true);
		user.setFirstName("test_firstName");
		user.setLastName("test_lastName");
		user.setUsername(username);
		user.setRole("FARMER");
		user.setMobileNumber(0771234567);
		user.setEmail("test_email");
		user.setAddressLine1("test_addressLine1");
		user.setAddressLine2("test_addressLine2");
		user.setAddressLine3("test_addressLine3");

		when(userRepository.findByUsername(username)).thenReturn(user);
		assertEquals(username, userManagementService.getUserDetails(username).getUsername());
	}

	@Test
	public void resetPasswordTest() {

		String username = "test_username";

		ResetPasswordByUserRequest resetPasswordByUserRequest = new ResetPasswordByUserRequest();
		resetPasswordByUserRequest.setOldPassword("test_oldPassword");
		resetPasswordByUserRequest.setUserNewPassword("test_userNewPassword");
		resetPasswordByUserRequest.setUserNewconfirmPassword("test_userNewPassword");

		User user = new User();
		user.setPassword(passwordEncoder.encode("test_oldPassword"));

		when(userRepository.findByUsername(username)).thenReturn(user);
		assertEquals(true,
				userManagementService.resetPassword(username, resetPasswordByUserRequest).isResponseStatus());
	}

	@Test
	public void updateUserDetailsTest() {

		String username = "test_username";
		UserDetailsUpdateRequest userDetailsUpdateRequest = new UserDetailsUpdateRequest();

		userDetailsUpdateRequest.setPassword("test_Password");
		userDetailsUpdateRequest.setFirstName("test_firstName");
		userDetailsUpdateRequest.setLastName("test_lastName");
		userDetailsUpdateRequest.setMobileNumber(0771234567);
		userDetailsUpdateRequest.setAddressLine1("test_addressLine1");
		userDetailsUpdateRequest.setAddressLine2("test_addressLine2");
		userDetailsUpdateRequest.setAddressLine3("test_addressLine3");

		User user = new User();
		user.setPassword(passwordEncoder.encode("test_Password"));
		user.setUsername(username);

		Optional<User> optiobalUserByMobileNumber = Optional.empty();

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(userRepository.findByMobileNumber(userDetailsUpdateRequest.getMobileNumber())).thenReturn(user);
		when(userRepository.findBymobileNumber(userDetailsUpdateRequest.getMobileNumber()))
				.thenReturn(optiobalUserByMobileNumber);

		assertEquals(true,
				userManagementService.updateUserDetails(username, userDetailsUpdateRequest).isResponseStatus());
	}

	@Test
	public void deactivateAccountTest() {

		String username = "test_username";

		User user = new User();
		user.setRole("FARMER");

		List<Shop> shops = null;

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(shopRepository.findByShopStatus(true)).thenReturn(shops);

		assertEquals(true, userManagementService.deactivateAccount(username).isResponseStatus());
	}

	@Test
	public void setProfilePictureTest() throws IOException {

		String username = "test_username";
		MultipartFile profilePictureByUser = new MockMultipartFile("test_file_name", "test_original_file_name",
				"image/PNG", new byte[1048576]);

		User user = new User();
		ProfilePicture profilePicture = null;

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(profilePictureRepository.findByUser(user)).thenReturn(profilePicture);

		assertEquals(true, userManagementService.setProfilePicture(username, profilePictureByUser).isResponseStatus());
	}
}
