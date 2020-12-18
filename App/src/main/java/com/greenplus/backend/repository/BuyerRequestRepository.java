package com.greenplus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.BuyerRequest;
import com.greenplus.backend.model.User;

@Repository
public interface BuyerRequestRepository extends JpaRepository<BuyerRequest, Integer> {

	BuyerRequest findByBuyerRequestId(int buyerRequestId);

	void deleteByBuyerRequestId(int buyerRequestId);

	List<BuyerRequest> findByBuyerRequestStatus(boolean buyerRequestStatus);

	List<BuyerRequest> findByUser(User username);
	
	List<BuyerRequest> findByUserAndBuyerRequestStatus(User user, boolean buyerRequestStatus);

}
