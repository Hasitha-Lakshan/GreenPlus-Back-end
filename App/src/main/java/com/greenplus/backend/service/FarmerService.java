package com.greenplus.backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenplus.backend.dto.BuyerRequestDetailsPublicResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.dto.ShopCreatingRequest;
import com.greenplus.backend.dto.ShopDashboardResponse;
import com.greenplus.backend.dto.ShopDetailsResponse;
import com.greenplus.backend.dto.ShopUpdateRequest;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.Order;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.ShopPicture;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.OrderRepository;
import com.greenplus.backend.repository.ShopPictureRepository;
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

	@Autowired
	private ShopPictureRepository shopPictureRepository;

	@Autowired
	private OrderRepository orderRepository;

	public Response shopCreating(MultipartFile shopPicture, ShopCreatingRequest shopCreatingRequest)
			throws IOException {

		if (shopCreatingRequest != null) {

			User user = userRepository.findByUsername(shopCreatingRequest.getUsername());

			if (user == null) {

				response.setResponseBody("The user not found, Shop creating is failed!");
				response.setResponseStatus(false);

				return response;
			}

			else if (user != null && user.getRole().equals("FARMER")) {

				if (shopPicture != null) {

					if (shopPicture.getSize() >= 1.5e+6) {

						response.setResponseBody("Exceeds the maximum size of shop picture, Shop creating is failed!");
						response.setResponseStatus(false);

						return response;

					} else if (!shopPicture.getContentType().startsWith("image")) {

						response.setResponseBody(
								"Does not support the file format of shop picture, Shop creating is failed!");
						response.setResponseStatus(false);

						return response;

					} else {

						Shop shop = new Shop();

						shop.setTitle(shopCreatingRequest.getTitle());
						shop.setCategory(shopCreatingRequest.getCategory());
						shop.setSubCategory(shopCreatingRequest.getSubCategory());
						shop.setDescription(shopCreatingRequest.getDescription());
						shop.setUnit(shopCreatingRequest.getUnit());
						shop.setPriceOfOneUnit(shopCreatingRequest.getPriceOfOneUnit());
						shop.setLocation(shopCreatingRequest.getLocation());
						shop.setCreatedDate(shopCreatingRequest.getCreatedDate());
						shop.setDeliveryDays(shopCreatingRequest.getDeliveryDays());
						shop.setShopStatus(true);
						shop.setUser(user);

						shopRepository.save(shop);

						ShopPicture newProfilePicture = new ShopPicture();

						newProfilePicture.setName(shopPicture.getOriginalFilename());
						newProfilePicture.setType(shopPicture.getContentType());
						newProfilePicture.setPictureBytes(compressBytes(shopPicture.getBytes()));
						newProfilePicture.setShop(shop);

						shopPictureRepository.save(newProfilePicture);

						response.setResponseBody("Shop created successfully!");
						response.setResponseStatus(true);

						return response;
					}

				} else {
					response.setResponseBody("Shop picture not found!");
					response.setResponseStatus(false);

					return response;
				}

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

	private ShopDetailsResponse mapFromShopToShopDetailsResponseDto(Shop shop) {

		ShopDetailsResponse shopDetailsResponse = new ShopDetailsResponse();

		shopDetailsResponse.setShopId(shop.getShopId());
		shopDetailsResponse.setTitle(shop.getTitle());
		shopDetailsResponse.setCategory(shop.getCategory());
		shopDetailsResponse.setSubCategory(shop.getSubCategory());
		shopDetailsResponse.setDescription(shop.getDescription());
		shopDetailsResponse.setUnit(shop.getUnit());
		shopDetailsResponse.setPriceOfOneUnit(shop.getPriceOfOneUnit());
		shopDetailsResponse.setLocation(shop.getLocation());
		shopDetailsResponse.setCreatedDate(shop.getCreatedDate());
		shopDetailsResponse.setDeliveryDays(shop.getDeliveryDays());
		shopDetailsResponse.setShopStatus(shop.isShopStatus());

		if (shop.getShopPicture() != null) {
			shopDetailsResponse.setPictureName(shop.getShopPicture().getName());
			shopDetailsResponse.setPictureType(shop.getShopPicture().getType());
			shopDetailsResponse.setPictureBytes(uncompressPictureBytes(shop.getShopPicture().getPictureBytes()));
		}

		return shopDetailsResponse;
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

	public ShopDetailsResponse getShopsByShopId(int shopId) {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			return this.mapFromShopToShopDetailsResponseDto(shop);

		} else {
			return null;
		}
	}

	public Response shopUpdate(int shopId, ShopUpdateRequest shopUpdateRequest, MultipartFile shopPicture)
			throws IOException {

		Shop shop = shopRepository.findByShopId(shopId);

		if (shop != null) {

			shop.setTitle(shopUpdateRequest.getTitle());
			shop.setCategory(shopUpdateRequest.getCategory());
			shop.setSubCategory(shopUpdateRequest.getSubCategory());
			shop.setDescription(shopUpdateRequest.getDescription());
			shop.setUnit(shopUpdateRequest.getUnit());
			shop.setPriceOfOneUnit(shopUpdateRequest.getPriceOfOneUnit());
			shop.setLocation(shopUpdateRequest.getLocation());
			shop.setDeliveryDays(shopUpdateRequest.getDeliveryDays());
			shop.setShopStatus(shopUpdateRequest.isShopStatus());

			if (shopPicture != null) {

				if (shopPicture.getSize() >= 1.5e+6) {

					response.setResponseBody("Exceeds the maximum size of shop picture, Shop creating is failed!");
					response.setResponseStatus(false);

					return response;

				} else if (!shopPicture.getContentType().startsWith("image")) {

					response.setResponseBody(
							"Does not support the file format of shop picture, Shop creating is failed!");
					response.setResponseStatus(false);

					return response;

				} else {
					ShopPicture shopPictureFromDatabse = shopPictureRepository.findByShop(shop);

					if (shopPictureFromDatabse != null) {

						shopPictureFromDatabse.setName(shopPicture.getOriginalFilename());
						shopPictureFromDatabse.setType(shopPicture.getContentType());
						shopPictureFromDatabse.setPictureBytes(compressBytes(shopPicture.getBytes()));
						shopPictureFromDatabse.setShop(shop);

						shopPictureRepository.save(shopPictureFromDatabse);

						response.setResponseBody("Profile picture updated successfully!");
						response.setResponseStatus(true);

						return response;

					} else {

						ShopPicture newShopPicture = new ShopPicture();

						newShopPicture.setName(shopPicture.getOriginalFilename());
						newShopPicture.setType(shopPicture.getContentType());
						newShopPicture.setPictureBytes(compressBytes(shopPicture.getBytes()));
						newShopPicture.setShop(shop);

						shopPictureRepository.save(newShopPicture);

						response.setResponseBody("New profile picture setting is successfull!");
						response.setResponseStatus(true);

						return response;
					}
				}
			}

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

			List<Order> orders = orderRepository.findByShop(shop);

			if (orders.isEmpty()) {

				shopRepository.deleteByShopId(shop.getShopId());

				response.setResponseBody("Shop successfully deleted!");
				response.setResponseStatus(true);

				return response;

			} else {

				int countOfIncompleteOrders = 0;

				for (Order checkOrder : orders) {

					if (!checkOrder.getOrderStatus().equals("COMPLETE")) {
						countOfIncompleteOrders++;
					}
				}

				if (countOfIncompleteOrders == 0) {

					shopRepository.deleteByShopId(shop.getShopId());

					response.setResponseBody("Shop successfully deleted!");
					response.setResponseStatus(true);

					return response;

				} else {
					response.setResponseBody("The shop has incompleted orders, Shop delete failed!");
					response.setResponseStatus(false);

					return response;
				}
			}

		} else {

			response.setResponseBody("The shop does not exsit, Shop delete failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	private BuyerRequestDetailsPublicResponse mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto(
			BuyerRequest buyerRequest) {

		BuyerRequestDetailsPublicResponse buyerRequestDetailsPublicResponse = new BuyerRequestDetailsPublicResponse();

		buyerRequestDetailsPublicResponse.setBuyerRequestId(buyerRequest.getBuyerRequestId());
		buyerRequestDetailsPublicResponse.setUsername(buyerRequest.getUser().getUsername());
		buyerRequestDetailsPublicResponse.setTitle(buyerRequest.getTitle());
		buyerRequestDetailsPublicResponse.setCategory(buyerRequest.getCategory());
		buyerRequestDetailsPublicResponse.setSubCategory(buyerRequest.getSubCategory());
		buyerRequestDetailsPublicResponse.setDescription(buyerRequest.getDescription());
		buyerRequestDetailsPublicResponse.setUnit(buyerRequest.getUnit());
		buyerRequestDetailsPublicResponse.setQuantity(buyerRequest.getQuantity());
		buyerRequestDetailsPublicResponse.setPrice(buyerRequest.getPrice());
		buyerRequestDetailsPublicResponse.setLocation(buyerRequest.getLocation());
		buyerRequestDetailsPublicResponse.setCreatedDate(buyerRequest.getCreatedDate());
		buyerRequestDetailsPublicResponse.setExpectDays(buyerRequest.getExpectDays());

		return buyerRequestDetailsPublicResponse;
	}

	public List<BuyerRequestDetailsPublicResponse> getAllBuyerRequests() {

		List<BuyerRequest> buyerRequests = buyerRequestRepository.findByBuyerRequestStatus(true);

		return buyerRequests.stream().map(this::mapFromBuyerRequestToBuyerRequestDetailsPublicResponseDto)
				.collect(Collectors.toList());
	}

	private ShopDashboardResponse mapFromShopToShopDashboardResponseDto(Shop shop) {

		ShopDashboardResponse shopDashboardResponse = new ShopDashboardResponse();

		shopDashboardResponse.setShopId(shop.getShopId());
		shopDashboardResponse.setTitle(shop.getTitle());
		shopDashboardResponse.setCreatedDate(shop.getCreatedDate());
		shopDashboardResponse.setShopStatus(shop.isShopStatus());

		return shopDashboardResponse;
	}

	public List<ShopDashboardResponse> getAllAShopsByUser(String username) {

		User user = userRepository.findByUsername(username);

		if (user != null && user.getRole().equals("FARMER")) {

			List<Shop> shops = shopRepository.findByUser(user);

			return shops.stream().map(this::mapFromShopToShopDashboardResponseDto).collect(Collectors.toList());
		} else {
			return null;
		}
	}
}
