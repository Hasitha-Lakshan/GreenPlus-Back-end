package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ShopCardDetailsResponse;
import com.greenplus.backend.dto.ShopDetailsPublicResponse;
import com.greenplus.backend.dto.UserDetailsPublicResponse;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class PublicService {

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private UserRepository userRepository;

	/////// Functions for Unauthorized Users //////////////////////////

	public List<ShopCardDetailsResponse> getAllShops() {

		List<Shop> shops = shopRepository.findByShopStatus(true);

		return shops.stream().map(this::mapFromShopCardToDto).collect(Collectors.toList());

	}

	private ShopCardDetailsResponse mapFromShopCardToDto(Shop shop) {

		ShopCardDetailsResponse shopCardDetailsResponse = new ShopCardDetailsResponse();

		shopCardDetailsResponse.setShopId(shop.getShopId());
		shopCardDetailsResponse.setTitle(shop.getTitle());
		shopCardDetailsResponse.setPrice(shop.getPrice());
		shopCardDetailsResponse.setLocation(shop.getLocation());
		shopCardDetailsResponse.setCreatedDate(shop.getCreatedDate());

		return shopCardDetailsResponse;
	}

	public ShopDetailsPublicResponse getShopsByShopId(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {
			return this.mapFromShopDetailsToDto(shop);
		} else {
			return null;
		}
	}

	private ShopDetailsPublicResponse mapFromShopDetailsToDto(Shop shop) {

		ShopDetailsPublicResponse shopDetailsPublicResponse = new ShopDetailsPublicResponse();

		shopDetailsPublicResponse.setTitle(shop.getTitle());
		shopDetailsPublicResponse.setCategory(shop.getCategory());
		shopDetailsPublicResponse.setSubCategory(shop.getSubCategory());
		shopDetailsPublicResponse.setQuantity(shop.getQuantity());
		shopDetailsPublicResponse.setPrice(shop.getPrice());
		shopDetailsPublicResponse.setDescription(shop.getDescription());
		shopDetailsPublicResponse.setLocation(shop.getLocation());
		shopDetailsPublicResponse.setDeliveryTime(shop.getDeliveryTime());
		shopDetailsPublicResponse.setCreatedDate(shop.getCreatedDate());
		shopDetailsPublicResponse.setCreatedTime(shop.getCreatedTime());
		shopDetailsPublicResponse.setUsername(shop.getUser().getUsername());

		return shopDetailsPublicResponse;
	}

	public List<ShopCardDetailsResponse> getShopsByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			List<Shop> shops = shopRepository.findByUser(user);

			return shops.stream().map(this::mapFromShopCardToDto).collect(Collectors.toList());

		} else {
			return null;
		}
	}

	public UserDetailsPublicResponse getUserDetailsPublic(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.isAccountStatus() == true) {

			return this.mapFromUserDetailsPublicToDto(user);
		} else {

			return null;
		}
	}

	private UserDetailsPublicResponse mapFromUserDetailsPublicToDto(User user) {

		UserDetailsPublicResponse userDetailsPublicResponse = new UserDetailsPublicResponse();

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

}
