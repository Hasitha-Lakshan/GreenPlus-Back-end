package com.greenplus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.BuyerRequest;

@Repository
public interface BuyerRequestRepository extends JpaRepository<BuyerRequest, Integer> {

}
