package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.ShopUpdateRequest;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class FarmerService {

	@Autowired
	private Response response;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	public Response shopCreating(ShopCreatingRequest shopCreatingRequest) {

		if (shopCreatingRequest != null) {

			User user = userRepository.findByUsername(shopCreatingRequest.getUsername());

			if (user.getRole().equals("FARMER")) {

				Shop shop = new Shop();

				shop.setTitle(shopCreatingRequest.getTitle());
				shop.setCategory(shopCreatingRequest.getCategory());
				shop.setSubCategory(shopCreatingRequest.getSubCategory());
				shop.setDescription(shopCreatingRequest.getDescription());
				shop.setQuantity(shopCreatingRequest.getQuantity());
				shop.setPrice(shopCreatingRequest.getPrice());
				shop.setLocation(shopCreatingRequest.getLocation());
				shop.setCreatedDate(shopCreatingRequest.getCreatedDate());
				shop.setCreatedTime(shopCreatingRequest.getCreatedTime());
				shop.setDeliveryTime(shopCreatingRequest.getDeliveryTime());
				shop.setShopStatus(true);
				shop.setUser(user);

				shopRepository.save(shop);

				response.setResponseBody("Shop created successfully!");
				response.setResponseStatus(true);

				return response;
			} else {
				response.setResponseBody("Only farmers are allowed to create shops!");
				response.setResponseStatus(false);

				return response;
			}

		} else {

			response.setResponseBody("Shop creating failed!");
			response.setResponseStatus(false);

			return response;
		}

	}

	private ShopDetailsResponse mapFromShopToDto(Shop shop) {

		ShopDetailsResponse shopDetailsResponse = new ShopDetailsResponse();

		shopDetailsResponse.setShopId(shop.getShopId());
		shopDetailsResponse.setTitle(shop.getTitle());
		shopDetailsResponse.setCategory(shop.getCategory());
		shopDetailsResponse.setSubCategory(shop.getSubCategory());
		shopDetailsResponse.setDescription(shop.getDescription());
		shopDetailsResponse.setQuantity(shop.getQuantity());
		shopDetailsResponse.setPrice(shop.getPrice());
		shopDetailsResponse.setLocation(shop.getLocation());
		shopDetailsResponse.setCreatedDate(shop.getCreatedDate());
		shopDetailsResponse.setCreatedTime(shop.getCreatedTime());
		shopDetailsResponse.setDeliveryTime(shop.getDeliveryTime());
		shopDetailsResponse.setShopStatus(shop.isShopStatus());

		return shopDetailsResponse;
	}

	public List<ShopDetailsResponse> getShopsByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.getRole().equals("FARMER")) {

			List<Shop> shops = shopRepository.findByUser(user);

			return shops.stream().map(this::mapFromShopToDto).collect(Collectors.toList());
		} else {
			return null;
		}

	}

	public ShopDetailsResponse getShopsByShopId(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			return this.mapFromShopToDto(shop);

		} else {
			return null;
		}
	}

	public Response shopUpdate(int shopId, ShopUpdateRequest shopUpdateRequest) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			shop.setTitle(shopUpdateRequest.getTitle());
			shop.setCategory(shopUpdateRequest.getCategory());
			shop.setSubCategory(shopUpdateRequest.getSubCategory());
			shop.setDescription(shopUpdateRequest.getDescription());
			shop.setQuantity(shopUpdateRequest.getQuantity());
			shop.setPrice(shopUpdateRequest.getPrice());
			shop.setLocation(shopUpdateRequest.getLocation());
			shop.setDeliveryTime(shopUpdateRequest.getDeliveryTime());
			shop.setShopStatus(shopUpdateRequest.isShopStatus());

			shopRepository.save(shop);

			response.setResponseBody("Shop successfully updated!");
			response.setResponseStatus(true);

			return response;

		} else {

			response.setResponseBody("The shop does not exsit, Shop update failed!");
			response.setResponseStatus(false);

			return response;
		}

	}

	@Transactional
	public Response shopDelete(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			shopRepository.deleteByShopId(shopId);

			response.setResponseBody("Shop successfully deleted!");
			response.setResponseStatus(true);

			return response;
		}

		response.setResponseBody("The shop does not exsit, Shop delete failed!");
		response.setResponseStatus(false);

		return response;

	}

}
