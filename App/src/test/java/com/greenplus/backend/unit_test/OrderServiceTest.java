package com.greenplus.backend.unit_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.greenplus.backend.dto.OrderCreatingRequest;
import com.greenplus.backend.dto.OrderStatusChangeRequest;
import com.greenplus.backend.model.Order;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.OrderRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.service.OrderService;

@SpringBootTest
public class OrderServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ShopRepository shopRepository;

	@MockBean
	private OrderRepository orderRepository;

	@Autowired
	private OrderService orderService;

	@Test
	public void orderRequirementDetailsTest() {

		int shopId = 1;
		String buyerUsername = "test_buyer_username";

		User buyer = new User();

		buyer.setUsername(buyerUsername);
		buyer.setAddressLine1("test_addressLine1");
		buyer.setAddressLine2("test_addressLine2");
		buyer.setAddressLine3("test_addressLine3");

		Shop shop = new Shop();

		shop.setTitle("test_title");
		shop.setUnit("test_unit");
		shop.setPriceOfOneUnit(100);
		shop.setLocation("test_location");
		shop.setCreatedDate(new Date());
		shop.setDeliveryDays(1);
		shop.setUser(buyer);

		when(userRepository.findByUsername(buyerUsername)).thenReturn(buyer);
		when(shopRepository.findByShopId(shopId)).thenReturn(shop);

		assertEquals(1, orderService.orderRequirementDetails(shopId, buyerUsername).getDeliveryDays());
	}

	@Test
	public void createOrderTest() {

		OrderCreatingRequest orderCreatingRequest = new OrderCreatingRequest();

		orderCreatingRequest.setDeliveryAddressLine1("test_deliveryAddressLine1");
		orderCreatingRequest.setDeliveryAddressLine2("test_deliveryAddressLine2");
		orderCreatingRequest.setDeliveryAddressLine3("test_deliveryAddressLine3");
		orderCreatingRequest.setDeliveryLocation("test_deliveryLocation");
		orderCreatingRequest.setNote("test_note");
		orderCreatingRequest.setUnit("test_unit");
		orderCreatingRequest.setQuantity(1);
		orderCreatingRequest.setTotalPrice(1000);
		orderCreatingRequest.setCreatedDate(new Date());
		orderCreatingRequest.setDueDate(new Date());

		User buyer = new User();
		buyer.setUsername("test_username");
		buyer.setRole("BUYER");

		Shop shop = new Shop();
		shop.setUser(buyer);
		shop.setDeliveryDays(1);
		shop.setTitle("test_title");
		shop.setShopStatus(true);

		when(shopRepository.findByShopId(orderCreatingRequest.getShopId())).thenReturn(shop);
		when(userRepository.findByUsername(orderCreatingRequest.getBuyerUsername())).thenReturn(buyer);

		assertEquals(true, orderService.createOrder(orderCreatingRequest).isResponseStatus());
	}

	@Test
	public void getOrdersByUserTest() {

		String username = "test_username";
		String orderStatus = "INPROGRESS";

		User user = new User();
		user.setRole("BUYER");

		Order order = new Order();
		order.setOrderId(1);
		order.setShopTitle("test_shopTitle");
		order.setCreatedDate(new Date());

		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(orderRepository.findByUserAndOrderStatus(user, orderStatus)).thenReturn(orders);

		assertEquals(1, orderService.getOrdersByUser(username, orderStatus).size());
	}

	@Test
	public void getOrdersByFarmerTest() {

		String username = "test_username";
		String orderStatus = "INPROGRESS";

		User user = new User();
		user.setRole("FARMER");

		Order order = new Order();
		order.setOrderId(1);
		order.setShopTitle("test_shopTitle");
		order.setCreatedDate(new Date());

		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(orderRepository.findByUserAndOrderStatus(user, orderStatus)).thenReturn(orders);

		assertEquals(1, orderService.getOrdersByFarmer(username, orderStatus).size());
	}

	@Test
	public void getOrderDetailsByOrderIdTest() {

		int orderId = 1;

		User user = new User();
		user.setUsername("test_username");

		Shop shop = new Shop();
		shop.setCategory("test_category");
		shop.setSubCategory("test_subCategory");

		Order order = new Order();
		order.setUser(user);
		order.setShop(shop);
		order.setShopTitle("test_shopTitle");
		order.setFarmerUsername("test_farmerUsername");
		order.setNote("test_note");
		order.setQuantity(1);
		order.setUnit("test_unit");
		order.setTotalPrice(1000);
		order.setCreatedDate(new Date());
		order.setDueDate(new Date());
		order.setCompletedDate(new Date());
		order.setDeliveryDays(1);
		order.setOrderStatus("test_orderStatus");
		order.setDeliveryAddressLine1("test_deliveryAddressLine1");
		order.setDeliveryAddressLine2("test_deliveryAddressLine2");
		order.setDeliveryAddressLine3("test_deliveryAddressLine3");
		order.setDeliveryLocation("test_deliveryLocation");

		when(orderRepository.findByOrderId(orderId)).thenReturn(order);
		assertEquals("test_username", orderService.getOrderDetailsByOrderId(orderId).getBuyerUsername());
	}

	@Test
	public void changeOrderStatusByOrderIdTest() {

		OrderStatusChangeRequest orderStatusChangeRequest = new OrderStatusChangeRequest();

		orderStatusChangeRequest.setCompletedDate(new Date());
		orderStatusChangeRequest.setOrderStatus("COMPLETE");
		orderStatusChangeRequest.setOrderId(1);
		orderStatusChangeRequest.setUsername("test_username");

		User user = new User();
		user.setRole("BUYER");
		user.setUsername("test_username");

		Order updatingOrder = new Order();
		updatingOrder.setOrderStatus("ACTIVE");
		updatingOrder.setUser(user);

		when(userRepository.findByUsername(orderStatusChangeRequest.getUsername())).thenReturn(user);
		when(orderRepository.findByOrderId(orderStatusChangeRequest.getOrderId())).thenReturn(updatingOrder);

		assertEquals(true, orderService.changeOrderStatusByOrderId(orderStatusChangeRequest).isResponseStatus());
	}

	@Test
	public void orderDeleteTest() {

		int orderId = 1;

		Order order = new Order();
		order.setOrderStatus("INPROGRESS");

		when(orderRepository.findByOrderId(orderId)).thenReturn(order);
		assertEquals(true, orderService.orderDelete(orderId).isResponseStatus());
	}

	@Test
	public void changeOrderStatusToLateByOrderIdTest() {

		int orderId = 1;

		Order updatingOrder = new Order();
		updatingOrder.setDueDate(new Date());
		updatingOrder.setOrderStatus("ACTIVE");

		when(orderRepository.findByOrderId(orderId)).thenReturn(updatingOrder);
		assertEquals(true, orderService.changeOrderStatusToLateByOrderId(orderId).isResponseStatus());
	}
}
