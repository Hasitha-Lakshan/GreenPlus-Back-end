package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.BuyerRequestDetailsPublicResponse;
import com.greenplus.backend.dto.BuyerRequestPublicResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.ShopUpdateRequest;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
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

	@Autowired
	private BuyerRequestRepository buyerRequestRepository;

	public Response shopCreating(ShopCreatingRequest shopCreatingRequest) {

		if (shopCreatingRequest != null) {

			User user = userRepository.findByUsername(shopCreatingRequest.getUsername());

			if (user == null) {

				response.setResponseBody("The user not found, Shop creating is failed!");
				response.setResponseStatus(false);

				return response;
			}

			else if (user != null && user.getRole().equals("FARMER")) {

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

	private ShopDetailsResponse mapFromShopToShopDetailsResponseDto(Shop shop) {

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

	public ShopDetailsResponse getShopsByShopId(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			return this.mapFromShopToShopDetailsResponseDto(shop);

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

	private BuyerRequestPublicResponse mapFromBuyerRequestToBuyerRequestPublicResponseDto(BuyerRequest buyerRequest) {

		BuyerRequestPublicResponse buyerRequestPublicResponse = new BuyerRequestPublicResponse();

		buyerRequestPublicResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestPublicResponse.setTitle(buyerRequest.getTitle());
		buyerRequestPublicResponse.setPrice(buyerRequest.getPrice());
		buyerRequestPublicResponse.setLocation(buyerRequest.getLocation());
		buyerRequestPublicResponse.setCreatedDate(buyerRequest.getCreatedDate());

		return buyerRequestPublicResponse;
	}

	public List<BuyerRequestPublicResponse> getAllBuyerRequestsPublic() {

		List<BuyerRequest> buyerRequests = buyerRequestRepository.findByBuyerRequestStatus(true);

		return buyerRequests.stream().map(this::mapFromBuyerRequestToBuyerRequestPublicResponseDto)
				.collect(Collectors.toList());

	}

	private BuyerRequestDetailsPublicResponse mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto(
			BuyerRequest buyerRequest) {

		BuyerRequestDetailsPublicResponse buyerRequestDetailsPublicResponse = new BuyerRequestDetailsPublicResponse();

		buyerRequestDetailsPublicResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDetailsPublicResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDetailsPublicResponse.setCategory(buyerRequest.getCategory());
		buyerRequestDetailsPublicResponse.setSubCategory(buyerRequest.getSubCategory());
		buyerRequestDetailsPublicResponse.setDescription(buyerRequest.getDescription());
		buyerRequestDetailsPublicResponse.setQuantity(buyerRequest.getQuantity());
		buyerRequestDetailsPublicResponse.setPrice(buyerRequest.getPrice());
		buyerRequestDetailsPublicResponse.setLocation(buyerRequest.getLocation());
		buyerRequestDetailsPublicResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDetailsPublicResponse.setCreatedTime(buyerRequest.getCreatedTime());
		buyerRequestDetailsPublicResponse.setDeliveryTime(buyerRequest.getDeliveryTime());

		return buyerRequestDetailsPublicResponse;
	}

	public BuyerRequestDetailsPublicResponse getBuyerRequestByBuyerRequestId(int buyerRequestId) {

		BuyerRequest buyerRequest = buyerRequestRepository.findByBuyerRequestId(buyerRequestId);

		if (buyerRequest != null) {

			return this.mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto(buyerRequest);

		} else {
			return null;
		}
	}

}
