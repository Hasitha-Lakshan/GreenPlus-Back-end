package com.greenplus.backend.dto;

public class BuyerRequestUpdateRequest {

	private String title;
	private String description;
	private String category;
	private String subCategory;
	private String unit;
	private int quantity;
	private float price;
	private String location;
	private int expectDays;
	private boolean buyerRequestStatus;

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public int getExpectDays() {
		return expectDays;
	}

	public void setExpectDays(int expectDays) {
		this.expectDays = expectDays;
	}

	public boolean isBuyerRequestStatus() {
		return buyerRequestStatus;
	}

	public void setBuyerRequestStatus(boolean buyerRequestStatus) {
		this.buyerRequestStatus = buyerRequestStatus;
	}

}
