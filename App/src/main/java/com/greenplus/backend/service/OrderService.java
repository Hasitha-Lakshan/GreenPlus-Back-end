package com.greenplus.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderRequirementResponse;
import com.greenplus.backend.dto.Response;
import com.greenplus.backend.model.Order;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.OrderRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private Response response;

	public OrderRequirementResponse orderRequirementDetails(int shopId, String buyerUsername) {

		User buyer = userRepository.findByUsername(buyerUsername);
		Shop shop = shopRepository.findByShopId(shopId);

		if (buyer != null && shop != null) {

			OrderRequirementResponse orderRequirementResponse = new OrderRequirementResponse();

			orderRequirementResponse.setShopTitle(shop.getTitle());
			orderRequirementResponse.setUnit(shop.getUnit());
			orderRequirementResponse.setPriceOfOneUnit(shop.getPriceOfOneUnit());
			orderRequirementResponse.setShopLocation(shop.getLocation());
			orderRequirementResponse.setCreatedDate(shop.getCreatedDate());
			orderRequirementResponse.setFarmerUsername(shop.getUser().getUsername());
			orderRequirementResponse.setDeliveryDays(shop.getDeliveryDays());
			orderRequirementResponse.setBuyerAddressLine1(buyer.getAddressLine1());
			orderRequirementResponse.setBuyerAddressLine2(buyer.getAddressLine2());
			orderRequirementResponse.setBuyerAddressLine3(buyer.getAddressLine3());

			return orderRequirementResponse;

		} else {

			return null;
		}
	}

	public Response createOrder(OrderCreatingRequest orderCreatingRequest) {

		if (orderCreatingRequest != null) {

			User buyer = userRepository.findByUsername(orderCreatingRequest.getBuyerUsername());
			Shop shop = shopRepository.findByShopId(orderCreatingRequest.getShopId());

			if (shop != null && shop.isShopStatus()) {

				if (buyer != null && buyer.getRole().equals("BUYER")) {

					Order newOrder = new Order();

					newOrder.setShop(shop);
					newOrder.setUser(buyer);
					newOrder.setShopTitle(shop.getTitle());
					newOrder.setDeliveryDays(shop.getDeliveryDays());
					newOrder.setOrderStatus("INPROGRESS");
					newOrder.setDeliveryAddressLine1(orderCreatingRequest.getDeliveryAddressLine1());
					newOrder.setDeliveryAddressLine2(orderCreatingRequest.getDeliveryAddressLine2());
					newOrder.setDeliveryAddressLine3(orderCreatingRequest.getDeliveryAddressLine3());
					newOrder.setDeliveryLocation(orderCreatingRequest.getDeliveryLocation());
					newOrder.setNote(orderCreatingRequest.getNote());
					newOrder.setUnit(orderCreatingRequest.getUnit());
					newOrder.setQuantity(orderCreatingRequest.getQuantity());
					newOrder.setTotalPrice(orderCreatingRequest.getTotalPrice());
					newOrder.setCreatedDate(orderCreatingRequest.getCreatedDate());
					newOrder.setDueDate(orderCreatingRequest.getDueDate());
					
					orderRepository.save(newOrder);

					response.setResponseBody("The order created successfully!");
					response.setResponseStatus(true);

					return response;

				} else {
					response.setResponseBody("Buyer validation is failed, order creating is failed!");
					response.setResponseStatus(false);

					return response;
				}

			} else {
				response.setResponseBody("Shop does not exsit, order creating is failed!");
				response.setResponseStatus(false);

				return response;
			}

		} else {
			response.setResponseBody("The user not found, Buyer request creating is failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

}
