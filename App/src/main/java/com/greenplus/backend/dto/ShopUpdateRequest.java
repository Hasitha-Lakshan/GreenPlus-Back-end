package com.greenplus.backend.dto;

public class ShopUpdateRequest {

	private String title;
	private String category;
	private String subCategory;
	private String description;
	private String unit;
	private float priceOfOneUnit;
	private String location;
	private int deliveryDays;
	private boolean shopStatus;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getPriceOfOneUnit() {
		return priceOfOneUnit;
	}

	public void setPriceOfOneUnit(float priceOfOneUnit) {
		this.priceOfOneUnit = priceOfOneUnit;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public boolean isShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(boolean shopStatus) {
		this.shopStatus = shopStatus;
	}

}
