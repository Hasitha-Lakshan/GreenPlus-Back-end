package com.greenplus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenplus.backend.model.Shop;
import com.greenplus.backend.model.ShopPicture;

@Repository
public interface ShopPictureRepository extends JpaRepository<ShopPicture, Integer> {

	ShopPicture findByShop(Shop shop);

}
