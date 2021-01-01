package com.greenplus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
