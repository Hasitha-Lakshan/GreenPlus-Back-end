package com.greenplus.backend.unit_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.greenplus.backend.dto.ShopCreatingRequest;
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
import com.greenplus.backend.service.FarmerService;

@SpringBootTest
public class FarmerServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ShopRepository shopRepository;

	@MockBean
	private BuyerRequestRepository buyerRequestRepository;

	@MockBean
	private ShopPictureRepository shopPictureRepository;

	@MockBean
	private OrderRepository orderRepository;

	@Autowired
	private FarmerService farmerService;

	@Test
	public void shopCreatingTest() throws IOException {

		MultipartFile shopPicture = new MockMultipartFile("test_file_name", "test_original_file_name", "image/PNG",
				new byte[1048576]);

		ShopCreatingRequest shopCreatingRequest = new ShopCreatingRequest();

		shopCreatingRequest.setTitle("test_title");
		shopCreatingRequest.setCategory("test_category");
		shopCreatingRequest.setSubCategory("test_subCategory");
		shopCreatingRequest.setDescription("test_description");
		shopCreatingRequest.setUnit("test_unit");
		shopCreatingRequest.setPriceOfOneUnit(100);
		shopCreatingRequest.setLocation("test_location");
		shopCreatingRequest.setCreatedDate(new Date());
		shopCreatingRequest.setDeliveryDays(1);

		User user = new User();
		user.setRole("FARMER");

		when(userRepository.findByUsername(shopCreatingRequest.getUsername())).thenReturn(user);
		assertEquals(true, farmerService.shopCreating(shopPicture, shopCreatingRequest).isResponseStatus());
	}

	@Test
	public void getShopsByShopIdTest() throws IOException {

		int shopId = 1;
		ShopPicture shopPicture = null;
		Shop shop = new Shop();

		shop.setShopId(1);
		shop.setTitle("test_title");
		shop.setCategory("test_category");
		shop.setSubCategory("test_subCategory");
		shop.setDescription("test_description");
		shop.setUnit("test_unit");
		shop.setPriceOfOneUnit(100);
		shop.setLocation("test_location");
		shop.setCreatedDate(new Date());
		shop.setDeliveryDays(1);
		shop.setShopStatus(true);
		shop.setShopPicture(shopPicture);

		when(shopRepository.findByShopId(shopId)).thenReturn(shop);
		assertEquals(1, farmerService.getShopsByShopId(shopId).getShopId());
	}

	@Test
	public void shopUpdateTest() throws IOException {

		int shopId = 1;
		MultipartFile shopPicture = null;
		ShopUpdateRequest shopUpdateRequest = new ShopUpdateRequest();

		shopUpdateRequest.setTitle("test_title");
		shopUpdateRequest.setCategory("test_category");
		shopUpdateRequest.setSubCategory("test_subCategory");
		shopUpdateRequest.setDescription("test_description");
		shopUpdateRequest.setUnit("test_unit");
		shopUpdateRequest.setPriceOfOneUnit(100);
		shopUpdateRequest.setLocation("test_location");
		shopUpdateRequest.setDeliveryDays(1);
		shopUpdateRequest.setShopStatus(true);

		Shop shop = new Shop();

		when(shopRepository.findByShopId(shopId)).thenReturn(shop);
		assertEquals(true, farmerService.shopUpdate(shopId, shopUpdateRequest, shopPicture).isResponseStatus());
	}

	@Test
	public void shopDeleteTest() {

		int shopId = 1;
		Shop shop = new Shop();

		Order order = new Order();
		order.setOrderStatus("ACTIVE");

		List<Order> orders = new ArrayList<Order>();
		orders.add(order);

		when(shopRepository.findByShopId(shopId)).thenReturn(shop);
		when(orderRepository.findByShop(shop)).thenReturn(orders);

		assertEquals(false, farmerService.shopDelete(shopId).isResponseStatus());
	}

	@Test
	public void getAllBuyerRequestsTest() {

		List<BuyerRequest> buyerRequests = new ArrayList<BuyerRequest>();

		when(buyerRequestRepository.findByBuyerRequestStatus(true)).thenReturn(buyerRequests);
		assertEquals(true, farmerService.getAllBuyerRequests().isEmpty());
	}

	@Test
	public void getAllAShopsByUserTest() {

		String username = "test_username";

		User user = new User();
		user.setRole("FARMER");

		List<Shop> shops = new ArrayList<Shop>();

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(shopRepository.findByUser(user)).thenReturn(shops);

		assertEquals(true, farmerService.getAllAShopsByUser(username).isEmpty());
	}
}
