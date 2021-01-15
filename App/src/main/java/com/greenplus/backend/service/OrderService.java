package com.greenplus.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderDashboardResponse;
import com.greenplus.backend.dto.OrderDetailsResponse;
import com.greenplus.backend.dto.OrderRequirementResponse;
import com.greenplus.backend.dto.OrderStatusChangeRequest;
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

	public OrderDetailsResponse getOrderDetailsByOrderId(int orderId) {

		Order order = orderRepository.findByOrderId(orderId);

		if (order != null) {

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

			orderDetailsResponse.setShopTitle(order.getShopTitle());
			orderDetailsResponse.setCategory(order.getShop().getCategory());
			orderDetailsResponse.setSubCategory(order.getShop().getSubCategory());
			orderDetailsResponse.setFarmerUsername(order.getFarmerUsername());
			orderDetailsResponse.setBuyerUsername(order.getUser().getUsername());
			orderDetailsResponse.setNote(order.getNote());
			orderDetailsResponse.setQuantity(order.getQuantity());
			orderDetailsResponse.setUnit(order.getUnit());
			orderDetailsResponse.setTotalPrice(order.getTotalPrice());
			orderDetailsResponse.setCreatedDate(order.getCreatedDate());
			orderDetailsResponse.setDueDate(order.getDueDate());
			orderDetailsResponse.setCompletedDate(order.getCompletedDate());
			orderDetailsResponse.setDeliveryDays(order.getDeliveryDays());
			orderDetailsResponse.setOrderStatus(order.getOrderStatus());
			orderDetailsResponse.setDeliveryAddressLine1(order.getDeliveryAddressLine1());
			orderDetailsResponse.setDeliveryAddressLine2(order.getDeliveryAddressLine2());
			orderDetailsResponse.setDeliveryAddressLine3(order.getDeliveryAddressLine3());
			orderDetailsResponse.setDeliveryLocation(order.getDeliveryLocation());

			return orderDetailsResponse;

		} else {
			return null;
		}
	}

	public Response changeOrderStatusByOrderId(OrderStatusChangeRequest orderStatusChangeRequest) {

		Order updatingOrder = orderRepository.findByOrderId(orderStatusChangeRequest.getOrderId());
		User user = userRepository.findByUsername(orderStatusChangeRequest.getUsername());

		if (updatingOrder != null && user != null) {

			if (orderStatusChangeRequest.getOrderStatus().equals("ACTIVE") && user.getRole().equals("FARMER")
					&& updatingOrder.getFarmerUsername().equals(orderStatusChangeRequest.getUsername())
					&& !(updatingOrder.getOrderStatus().equals("ACTIVE")
							|| updatingOrder.getOrderStatus().equals("LATE")
							|| updatingOrder.getOrderStatus().equals("COMPLETE"))) {

				updatingOrder.setOrderStatus(orderStatusChangeRequest.getOrderStatus());
				orderRepository.save(updatingOrder);

				response.setResponseBody("Order status is successfully updated to ACTIVE!");
				response.setResponseStatus(true);

				return response;

			} else if (orderStatusChangeRequest.getOrderStatus().equals("COMPLETE")
					&& (user.getRole().equals("FARMER") || user.getRole().equals("BUYER"))
					&& updatingOrder.getUser().getUsername().equals(orderStatusChangeRequest.getUsername())
					&& !(updatingOrder.getOrderStatus().equals("COMPLETE")
							|| updatingOrder.getOrderStatus().equals("INPROGRESS"))) {

				updatingOrder.setCompletedDate(orderStatusChangeRequest.getCompletedDate());
				updatingOrder.setOrderStatus(orderStatusChangeRequest.getOrderStatus());
				orderRepository.save(updatingOrder);

				response.setResponseBody("Order status is successfully updated to COMPLETE!");
				response.setResponseStatus(true);

				return response;
			}

			else {

				response.setResponseBody("User validation failed,  order status updating is failed!");
				response.setResponseStatus(false);

				return response;
			}

		} else {

			response.setResponseBody("User or order does not exsit, order status updating is failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

	@Transactional
	public Response orderDelete(int orderId) {

		Order order = orderRepository.findByOrderId(orderId);

		if (order != null && order.getOrderStatus().equals("INPROGRESS")) {

			orderRepository.deleteByOrderId(orderId);

			response.setResponseBody("Order successfully deleted!");
			response.setResponseStatus(true);

			return response;
		}

		response.setResponseBody("The Order does not exsit, Order delete failed!");
		response.setResponseStatus(false);

		return response;
	}

	public Response changeOrderStatusToLateByOrderId(int orderId) {

		Order updatingOrder = orderRepository.findByOrderId(orderId);

		if (updatingOrder != null) {

			Date currentDate = new Date();
			Long timeDifference = updatingOrder.getDueDate().getTime() - currentDate.getTime();

			if (!updatingOrder.getOrderStatus().equals("LATE") && (timeDifference <= 0)) {

				updatingOrder.setOrderStatus("LATE");
				orderRepository.save(updatingOrder);

				response.setResponseBody("Order status successfully set to LATE!");
				response.setResponseStatus(true);

				return response;

			} else {
				response.setResponseBody("Order status already set to LATE, order status updating is failed!");
				response.setResponseStatus(false);

				return response;
			}

		} else {
			response.setResponseBody("Order does not exsit, order status updating to Late is failed!");
			response.setResponseStatus(false);

			return response;
		}
	}

}
