package com.greenplus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.User;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

	List<Shop> findByUser(User user);

	List<Shop> findByShopStatus(boolean shopStatus);

	Shop findByShopId(int shopId);

	void deleteByShopId(int shopId);
	
	List<Shop> findByUserAndShopStatus(User user, boolean shopStatus);

}
