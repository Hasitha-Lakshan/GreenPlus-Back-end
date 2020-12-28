package com.greenplus.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ProfilePictureResponse;
import com.greenplus.backend.dto.ShopCardDetailsResponse;
import com.greenplus.backend.dto.ShopDetailsPublicResponse;
import com.greenplus.backend.dto.UserDetailsPublicResponse;
import com.greenplus.backend.model.ProfilePicture;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.ProfilePictureRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class PublicService {

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfilePictureRepository profilePictureRepository;

	/////// Functions for Unauthorized Users //////////////////////////

	public List<ShopCardDetailsResponse> getAllShops() {

		List<Shop> shops = shopRepository.findByShopStatus(true);

		return shops.stream().map(this::mapFromShopToShopCardDetailsResponseDto).collect(Collectors.toList());

	}

	private ShopCardDetailsResponse mapFromShopToShopCardDetailsResponseDto(Shop shop) {

		ShopCardDetailsResponse shopCardDetailsResponse = new ShopCardDetailsResponse();

		shopCardDetailsResponse.setShopId(shop.getShopId());
		shopCardDetailsResponse.setTitle(shop.getTitle());
		shopCardDetailsResponse.setUnit(shop.getUnit());
		shopCardDetailsResponse.setPriceOfOneUnit(shop.getPriceOfOneUnit());
		shopCardDetailsResponse.setLocation(shop.getLocation());
		shopCardDetailsResponse.setCreatedDate(shop.getCreatedDate());
		shopCardDetailsResponse.setPictureName(shop.getShopPicture().getName());
		shopCardDetailsResponse.setPictureType(shop.getShopPicture().getType());
		shopCardDetailsResponse.setPictureBytes(uncompressPictureBytes(shop.getShopPicture().getPictureBytes()));

		return shopCardDetailsResponse;
	}

	public ShopDetailsPublicResponse getShopsByShopId(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null && (shop.isShopStatus() == true)) {
			return this.mapFromShopToShopDetailsPublicResponseDto(shop);
		} else {
			return null;
		}
	}

	private ShopDetailsPublicResponse mapFromShopToShopDetailsPublicResponseDto(Shop shop) {

		ShopDetailsPublicResponse shopDetailsPublicResponse = new ShopDetailsPublicResponse();

		shopDetailsPublicResponse.setTitle(shop.getTitle());
		shopDetailsPublicResponse.setCategory(shop.getCategory());
		shopDetailsPublicResponse.setSubCategory(shop.getSubCategory());
		shopDetailsPublicResponse.setUnit(shop.getUnit());
		shopDetailsPublicResponse.setPriceOfOneUnit(shop.getPriceOfOneUnit());
		shopDetailsPublicResponse.setDescription(shop.getDescription());
		shopDetailsPublicResponse.setLocation(shop.getLocation());
		shopDetailsPublicResponse.setDeliveryDays(shop.getDeliveryDays());
		shopDetailsPublicResponse.setCreatedDate(shop.getCreatedDate());
		shopDetailsPublicResponse.setUsername(shop.getUser().getUsername());
		shopDetailsPublicResponse.setPictureName(shop.getShopPicture().getName());
		shopDetailsPublicResponse.setPictureType(shop.getShopPicture().getType());
		shopDetailsPublicResponse.setPictureBytes(uncompressPictureBytes(shop.getShopPicture().getPictureBytes()));

		return shopDetailsPublicResponse;
	}

	public List<ShopCardDetailsResponse> getActiveShopsByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			List<Shop> shops = shopRepository.findByUserAndShopStatus(user, true);

			return shops.stream().map(this::mapFromShopToShopCardDetailsResponseDto).collect(Collectors.toList());

		} else {
			return null;
		}
	}

	public UserDetailsPublicResponse getUserDetailsPublic(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.isAccountStatus() == true) {

			return this.mapFromUserToUserDetailsPublicResponseDto(user);
		} else {

			return null;
		}
	}

	private UserDetailsPublicResponse mapFromUserToUserDetailsPublicResponseDto(User user) {

		UserDetailsPublicResponse userDetailsPublicResponse = new UserDetailsPublicResponse();

		userDetailsPublicResponse.setUserId(user.getUserId());
		userDetailsPublicResponse.setFirstName(user.getFirstName());
		userDetailsPublicResponse.setLastName(user.getLastName());
		userDetailsPublicResponse.setUsername(user.getUsername());
		userDetailsPublicResponse.setRole(user.getRole());
		userDetailsPublicResponse.setMobileNumber(user.getMobileNumber());
		userDetailsPublicResponse.setEmail(user.getEmail());
		userDetailsPublicResponse.setAddressLine1(user.getAddressLine1());
		userDetailsPublicResponse.setAddressLine2(user.getAddressLine2());
		userDetailsPublicResponse.setAddressLine3(user.getAddressLine3());

		return userDetailsPublicResponse;
	}

	public ProfilePictureResponse getProfilePicture(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			ProfilePicture profilePicture = profilePictureRepository.findByUser(user);

			if (profilePicture != null) {

				ProfilePictureResponse profilePictureResponse = new ProfilePictureResponse();

				profilePictureResponse.setName(profilePicture.getName());
				profilePictureResponse.setType(profilePicture.getType());
				profilePictureResponse.setPictureBytes(uncompressPictureBytes(profilePicture.getPictureBytes()));

				return profilePictureResponse;

			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	// Uncompress the picture bytes
	private byte[] uncompressPictureBytes(byte[] pictureBytes) {

		Inflater inflater = new Inflater();

		inflater.setInput(pictureBytes);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(pictureBytes.length);

		byte[] buffer = new byte[1048576];

		try {
			while (!inflater.finished()) {

				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();

		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}

		return outputStream.toByteArray();
	}

}
