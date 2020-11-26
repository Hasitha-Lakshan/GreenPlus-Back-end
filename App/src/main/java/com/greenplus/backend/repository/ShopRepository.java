package com.greenplus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
