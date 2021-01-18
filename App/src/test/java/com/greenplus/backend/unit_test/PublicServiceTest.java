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

import com.greenplus.backend.model.ProfilePicture;
import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.ShopPicture;
import com.greenplus.backend.model.User;
import com.greenplus.backend.repository.ProfilePictureRepository;
import com.greenplus.backend.repository.ShopRepository;
import com.greenplus.backend.repository.UserRepository;
import com.greenplus.backend.service.PublicService;

@SpringBootTest
public class PublicServiceTest {

	@MockBean
	private ShopRepository shopRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private ProfilePictureRepository profilePictureRepository;

	@Autowired
	private PublicService publicService;

	@Test
	public void getAllShopsTest() {

		ShopPicture shopPicture = null;
		Shop shop = new Shop();

		shop.setShopPicture(shopPicture);
		shop.setShopId(1);
		shop.setTitle("test_title");
		shop.setUnit("test_unit");
		shop.setPriceOfOneUnit(100);
		shop.setLocation("test_location");
		shop.setCreatedDate(new Date());

		List<Shop> shops = new ArrayList<Shop>();
		shops.add(shop);

		when(shopRepository.findByShopStatus(true)).thenReturn(shops);
		assertEquals(1, publicService.getAllShops().size());
	}

	@Test
	public void getShopsByShopIdTest() {

		int shopId = 1;
		ShopPicture shopPicture = null;
		User user = new User();
		user.setUsername("test_username");

		Shop shop = new Shop();

		shop.setShopPicture(shopPicture);
		shop.setShopStatus(true);
		shop.setTitle("test_title");
		shop.setCategory("test_category");
		shop.setSubCategory("test_subCategory");
		shop.setUnit("test_unit");
		shop.setPriceOfOneUnit(100);
		shop.setDescription("test_description");
		shop.setLocation("test_location");
		shop.setDeliveryDays(1);
		shop.setCreatedDate(new Date());
		shop.setUser(user);

		when(shopRepository.findByShopId(shopId)).thenReturn(shop);
		assertEquals("test_username", publicService.getShopsByShopId(shopId).getUsername());
	}

	@Test
	public void getActiveShopsByUserTest() {

		String username = "test_username";

		User user = new User();
		ShopPicture shopPicture = null;
		Shop shop = new Shop();

		shop.setShopPicture(shopPicture);
		shop.setShopId(1);
		shop.setTitle("test_title");
		shop.setUnit("test_unit");
		shop.setPriceOfOneUnit(100);
		shop.setLocation("test_location");
		shop.setCreatedDate(new Date());

		List<Shop> shops = new ArrayList<Shop>();
		shops.add(shop);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(shopRepository.findByUserAndShopStatus(user, true)).thenReturn(shops);

		assertEquals(1, publicService.getActiveShopsByUser(username).size());
	}

	@Test
	public void getUserDetailsPublicTest() {

		String username = "test_username";

		User user = new User();

		user.setAccountStatus(true);
		user.setUserId(1);
		user.setFirstName("test_firstName");
		user.setLastName("test_lastName");
		user.setUsername(username);
		user.setRole("FARMER");
		user.setMobileNumber(0771234567);
		user.setEmail("test_email");
		user.setAddressLine1("test_addressLine1");
		user.setAddressLine2("test_addressLine2");
		user.setAddressLine3("test_addressLine3");

		when(userRepository.findByUsername(username)).thenReturn(user);
		assertEquals(1, publicService.getUserDetailsPublic(username).getUserId());
	}

	@Test
	public void getProfilePictureTest() throws IOException {

		String username = "test_username";
		User user = new User();

		ProfilePicture profilePicture = new ProfilePicture();

		profilePicture.setName("test_file_name");
		profilePicture.setType("image/PNG");
		profilePicture.setPictureBytes(new byte[1048576]);

		when(userRepository.findByUsername(username)).thenReturn(user);
		when(profilePictureRepository.findByUser(user)).thenReturn(profilePicture);

		assertEquals("test_file_name", publicService.getProfilePicture(username).getName());
	}
}
