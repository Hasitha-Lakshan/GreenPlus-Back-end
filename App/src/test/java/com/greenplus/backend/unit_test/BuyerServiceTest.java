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

import com.greenplus.backend.dto.BuyerRequestCreatingRequest;
import com.greenplus.backend.dto.BuyerRequestUpdateRequest;
import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.BuyerRequestRepository;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.service.BuyerService;

@SpringBootTest
public class BuyerServiceTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private BuyerRequestRepository buyerRequestRepository;

	@Autowired
	private BuyerService buyerService;

	@Test
	public void buyerRequestCreatingTest() {

		BuyerRequestCreatingRequest buyerRequestCreatingRequest = new BuyerRequestCreatingRequest();

		buyerRequestCreatingRequest.setTitle("test_title");
		buyerRequestCreatingRequest.setDescription("test_description");
		buyerRequestCreatingRequest.setCategory("test_category");
		buyerRequestCreatingRequest.setSubCategory("test_subCategory");
		buyerRequestCreatingRequest.setUnit("test_unit");
		buyerRequestCreatingRequest.setQuantity(1);
		buyerRequestCreatingRequest.setPrice(1);
		buyerRequestCreatingRequest.setLocation("test_location");
		buyerRequestCreatingRequest.setCreatedDate(new Date());
		buyerRequestCreatingRequest.setExpectDays(1);
		buyerRequestCreatingRequest.setUsername("test_username");

		User testUser = new User();
		testUser.setRole("BUYER");

		when(userRepository.findByUsername(buyerRequestCreatingRequest.getUsername())).thenReturn(testUser);
		assertEquals(true, buyerService.buyerRequestCreating(buyerRequestCreatingRequest).isResponseStatus());
	}

	@Test
	public void buyerRequestDeleteTest() {

		int buyerRequestId = 1;
		BuyerRequest buyerRequest = new BuyerRequest();

		when(buyerRequestRepository.findByBuyerRequestId(buyerRequestId)).thenReturn(buyerRequest);
		assertEquals(true, buyerService.buyerRequestDelete(buyerRequestId).isResponseStatus());
	}

	@Test
	public void getActiveBuyerRequestsByUserTest() {

		String username = "test_username";

		User user = new User();
		user.setRole("BUYER");
		user.setUsername("test_username");

		BuyerRequest buyerRequest = new BuyerRequest();

		buyerRequest.setBuyerRequestId(1);
		buyerRequest.setUser(user);
		buyerRequest.setTitle("test_title");
		buyerRequest.setCategory("test_category");
		buyerRequest.setSubCategory("test_subCategory");
		buyerRequest.setDescription("test_description");
		buyerRequest.setUnit("test_unit");
		buyerRequest.setQuantity(1);
		buyerRequest.setPrice(100);
		buyerRequest.setLocation("test_location");
		buyerRequest.setCreatedDate(new Date());
		buyerRequest.setExpectDays(1);

		List<BuyerRequest> buyerRequests = new ArrayList<BuyerRequest>();
		buyerRequests.add(buyerRequest);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(buyerRequestRepository.findByUserAndBuyerRequestStatus(user, true)).thenReturn(buyerRequests);

		assertEquals(1, buyerService.getActiveBuyerRequestsByUser(username).size());

	}

	@Test
	public void getBuyerRequestByBuyerRequestIdTest() {

		int buyerRequestId = 1;
		BuyerRequest buyerRequest = new BuyerRequest();

		buyerRequest.setBuyerRequestId(buyerRequestId);
		buyerRequest.setTitle("test_title");
		buyerRequest.setDescription("test_description");
		buyerRequest.setCategory("test_category");
		buyerRequest.setSubCategory("test_subCategory");
		buyerRequest.setUnit("test_unit");
		buyerRequest.setQuantity(1);
		buyerRequest.setPrice(100);
		buyerRequest.setLocation("test_location");
		buyerRequest.setCreatedDate(new Date());
		buyerRequest.setExpectDays(1);
		buyerRequest.setBuyerRequestStatus(true);

		when(buyerRequestRepository.findByBuyerRequestId(buyerRequestId)).thenReturn(buyerRequest);
		assertEquals(1, buyerService.getBuyerRequestByBuyerRequestId(buyerRequestId).getBuyerRequestId());
	}

	@Test
	public void getAllBuyerRequestByUserTest() {

		String username = "test_username";

		User user = new User();
		user.setRole("BUYER");

		BuyerRequest buyerRequest = new BuyerRequest();

		buyerRequest.setBuyerRequestId(1);
		buyerRequest.setTitle("test_title");
		buyerRequest.setCreatedDate(new Date());
		buyerRequest.setBuyerRequestStatus(false);

		List<BuyerRequest> buyerRequests = new ArrayList<BuyerRequest>();
		buyerRequests.add(buyerRequest);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(buyerRequestRepository.findByUser(user)).thenReturn(buyerRequests);

		assertEquals(1, buyerService.getAllBuyerRequestByUser(username).size());
	}

	@Test
	public void buyerRequestUpdateTest() {

		int buyerRequestId = 1;
		BuyerRequestUpdateRequest buyerRequestUpdateRequest = new BuyerRequestUpdateRequest();

		buyerRequestUpdateRequest.setTitle("test_title");
		buyerRequestUpdateRequest.setCategory("test_category");
		buyerRequestUpdateRequest.setSubCategory("test_subCategory");
		buyerRequestUpdateRequest.setDescription("test_description");
		buyerRequestUpdateRequest.setUnit("test_unit");
		buyerRequestUpdateRequest.setQuantity(1);
		buyerRequestUpdateRequest.setPrice(100);
		buyerRequestUpdateRequest.setLocation("test_location");
		buyerRequestUpdateRequest.setExpectDays(1);
		buyerRequestUpdateRequest.setBuyerRequestStatus(true);

		BuyerRequest buyerRequest = new BuyerRequest();

		when(buyerRequestRepository.findByBuyerRequestId(buyerRequestId)).thenReturn(buyerRequest);
		assertEquals(true,
				buyerService.buyerRequestUpdate(buyerRequestId, buyerRequestUpdateRequest).isResponseStatus());
	}
}
