package com.greenplus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.Order;
import com.greenplus.backend.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	Order findByOrderId(int orderId);
	
	List<Order> findByUserAndOrderStatus(User buyer, String orderStatus);

	List<Order> findByFarmerUsernameAndOrderStatus(String farmerUsername, String orderStatus);

}
