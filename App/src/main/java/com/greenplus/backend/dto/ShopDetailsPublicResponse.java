package com.greenplus.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShopDetailsPublicResponse {

	private String title;
	private String category;
	private String subCategory;
	private String unit;
	private float priceOfOneUnit;
	private String description;
	private String location;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date createdDate;
	private int deliveryDays;
	private String username;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
