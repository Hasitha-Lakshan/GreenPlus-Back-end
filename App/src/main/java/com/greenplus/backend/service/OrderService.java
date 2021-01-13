package com.greenplus.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderDashboardResponse;
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

				if (buyer != null && (buyer.getRole().equals("BUYER") || buyer.getRole().equals("FARMER"))) {

					Order newOrder = new Order();

					newOrder.setShop(shop);
					newOrder.setUser(buyer);
					newOrder.setShopTitle(shop.getTitle());
					newOrder.setFarmerUsername(shop.getUser().getUsername());
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

	public List<OrderDashboardResponse> getOrdersByUser(String username, String orderStatus) {

		User user = userRepository.findByUsername(username);

		if (user != null) {

			if (user.getRole().equals("BUYER")) {

				List<Order> inprogressOrdersByBuyer = orderRepository.findByUserAndOrderStatus(user, orderStatus);

				return inprogressOrdersByBuyer.stream().map(this::mapFromOrdersByBuyerToOrderDashboardResponseDto)
						.collect(Collectors.toList());

			} else if (user.getRole().equals("FARMER")) {

				List<Order> inprogressOrdersFromShops = orderRepository.findByFarmerUsernameAndOrderStatus(username,
						orderStatus);

				return inprogressOrdersFromShops.stream().map(this::mapFromOrdersForShopsToOrderDashboardResponseDto)
						.collect(Collectors.toList());

			} else {
				return null;
			}

		} else {
			return null;
		}

	}

	public List<OrderDashboardResponse> getOrdersByFarmer(String username, String orderStatus) {

		User user = userRepository.findByUsername(username);

		if (user.getRole().equals("FARMER")) {

			List<Order> inprogressOrdersByFarmer = orderRepository.findByUserAndOrderStatus(user, orderStatus);

			return inprogressOrdersByFarmer.stream().map(this::mapFromOrdersByBuyerToOrderDashboardResponseDto)
					.collect(Collectors.toList());

		} else {
			return null;
		}

	}

	private OrderDashboardResponse mapFromOrdersByBuyerToOrderDashboardResponseDto(Order order) {

		OrderDashboardResponse orderDashboardResponse = new OrderDashboardResponse();

		orderDashboardResponse.setOrderId(order.getOrderId());
		orderDashboardResponse.setShopTitle(order.getShopTitle());
		orderDashboardResponse.setCreatedDate(order.getCreatedDate());

		return orderDashboardResponse;
	}

	private OrderDashboardResponse mapFromOrdersForShopsToOrderDashboardResponseDto(Order order) {

		OrderDashboardResponse orderDashboardResponse = new OrderDashboardResponse();

		orderDashboardResponse.setOrderId(order.getOrderId());
		orderDashboardResponse.setShopTitle(order.getShopTitle());
		orderDashboardResponse.setCreatedDate(order.getCreatedDate());

		return orderDashboardResponse;
	}

}
