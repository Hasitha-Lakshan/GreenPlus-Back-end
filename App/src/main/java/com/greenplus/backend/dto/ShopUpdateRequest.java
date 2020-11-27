package com.greenplus.backend.dto;

import java.time.LocalDate;

public class ShopUpdateRequest {

	private String title;
	private String category;
	private String subCategory;
	private String description;
	private int quantity;
	private float price;
	private String location;
	private LocalDate deliveryTime;
	private boolean shopStatus;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalDate deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public boolean isShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(boolean shopStatus) {
		this.shopStatus = shopStatus;
	}

}
