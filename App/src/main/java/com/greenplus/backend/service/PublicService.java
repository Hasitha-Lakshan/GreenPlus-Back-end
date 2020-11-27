package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.repository.ShopRepository;

@Service
public class PublicService {

	@Autowired
	private ShopRepository shopRepository;

	public List<ShopDetailsResponse> getAllShops() {

		List<Shop> shops = shopRepository.findByShopStatus(true);

		return shops.stream().map(this::mapFromShopToDto).collect(Collectors.toList());

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

}
