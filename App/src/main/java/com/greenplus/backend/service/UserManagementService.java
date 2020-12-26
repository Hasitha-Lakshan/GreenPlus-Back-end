package com.greenplus.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenplus.backend.dto.ResetPasswordByUserRequest;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.UserDetailsResponse;
import com.greenplus.backend.dto.UserDetailsUpdateRequest;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.ProfilePicture;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.ProfilePictureRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class UserManagementService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Response response;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private BuyerRequestRepository buyerRequestRepository;

	@Autowired
	private ProfilePictureRepository profilePictureRepository;

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

		userDetailsResponse.setFirstName(user.getFirstName());
		userDetailsResponse.setLastName(user.getLastName());
		userDetailsResponse.setUsername(user.getUsername());
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

			if (resetPasswordByUserRequest.getUserNewPassword()
					.equals(resetPasswordByUserRequest.getUserNewconfirmPassword())) {

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

		if (user != null && oldPassword != null) {
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {

				return true;

			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public Response updateUserDetails(String username, UserDetailsUpdateRequest userDetailsUpdateRequest) {

		if (validateUser(username, userDetailsUpdateRequest.getPassword())) {

			User user = userRepository.findByUsername(username);
			User userByMobileNumber = userRepository.findByMobileNumber(userDetailsUpdateRequest.getMobileNumber());
			Optional<User> optiobalUserByMobileNumber = userRepository
					.findBymobileNumber(userDetailsUpdateRequest.getMobileNumber());

			if (user.getUsername().equals(userByMobileNumber.getUsername()) || (optiobalUserByMobileNumber.isEmpty())) {

				user.setFirstName(userDetailsUpdateRequest.getFirstName());
				user.setLastName(userDetailsUpdateRequest.getLastName());
				user.setMobileNumber(userDetailsUpdateRequest.getMobileNumber());
				user.setAddressLine1(userDetailsUpdateRequest.getAddressLine1());
				user.setAddressLine2(userDetailsUpdateRequest.getAddressLine2());
				user.setAddressLine3(userDetailsUpdateRequest.getAddressLine3());

				userRepository.save(user);

				response.setResponseBody("User details updated successfully!");
				response.setResponseStatus(true);

				return response;

			} else {

				response.setResponseBody(
						"The mobile number is already using by another user, User details update is failed!");
				response.setResponseStatus(false);

				return response;
			}

		}

		else {

			response.setResponseBody("User validation failed, User details update is failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	public Response deactivateAccount(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			user.setAccountStatus(false);
			userRepository.save(user);

			if (user.getRole().equals("FARMER")) {

				List<Shop> shops = shopRepository.findByShopStatus(true);

				for (Shop shop : shops) {
					shop.setShopStatus(false);
					shopRepository.save(shop);
				}
			}

			if (user.getRole().equals("BUYER")) {

				List<BuyerRequest> buyerRequests = buyerRequestRepository.findByBuyerRequestStatus(true);

				for (BuyerRequest buyerRequest : buyerRequests) {
					buyerRequest.setBuyerRequestStatus(false);
					buyerRequestRepository.save(buyerRequest);
				}
			}

			response.setResponseBody("Account Successfully Deactivated!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody("Account Deactivate Failed!, username:" + username + " doest not exist");
			response.setResponseStatus(false);

			return response;
		}

	}

	// Save the images in database, Max file size is 1M
	public Response setProfilePicture(String username, MultipartFile profilePictureByUser) throws IOException {

		User user = userRepository.findByUsername(username);

		if (user != null && !profilePictureByUser.isEmpty()) {

			ProfilePicture profilePicture = profilePictureRepository.findByUser(user);

			if (profilePictureByUser.getSize() >= 1.5e+6) {

				response.setResponseBody("Exceeds the maximum size, profile picture setting is failed!");
				response.setResponseStatus(false);

				return response;

			} else if (!profilePictureByUser.getContentType().startsWith("image")) {

				response.setResponseBody("Does not support the file format, profile picture setting is failed!");
				response.setResponseStatus(false);

				return response;

			} else {

				if (profilePicture != null) {

					profilePicture.setName(profilePictureByUser.getOriginalFilename());
					profilePicture.setType(profilePictureByUser.getContentType());
					profilePicture.setPictureBytes(compressBytes(profilePictureByUser.getBytes()));
					profilePicture.setUser(user);

					profilePictureRepository.save(profilePicture);

					response.setResponseBody("Profile picture updated successfully!");
					response.setResponseStatus(true);

					return response;

				} else {

					ProfilePicture newProfilePicture = new ProfilePicture();

					newProfilePicture.setName(profilePictureByUser.getOriginalFilename());
					newProfilePicture.setType(profilePictureByUser.getContentType());
					newProfilePicture.setPictureBytes(compressBytes(profilePictureByUser.getBytes()));
					newProfilePicture.setUser(user);

					profilePictureRepository.save(newProfilePicture);

					response.setResponseBody("New profile picture setting is successfull!");
					response.setResponseStatus(true);

					return response;
				}
			}

		} else {

			response.setResponseBody(
					"User : " + username + " does not exist or invalid file, Profile picture setting is failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	// Compress the picture bytes
	private byte[] compressBytes(byte[] pictureBytes) {

		Deflater deflater = new Deflater();

		deflater.setInput(pictureBytes);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(pictureBytes.length);

		byte[] buffer = new byte[1048576];

		while (!deflater.finished()) {

			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();

		} catch (IOException e) {
		}

		return outputStream.toByteArray();
	}

}
