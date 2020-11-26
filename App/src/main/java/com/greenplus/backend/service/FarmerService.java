package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
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

}
