package com.greenplus.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderRequirementResponse {

	private String shopTitle;
	private String unit;
	private float priceOfOneUnit;
	private String shopLocation;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date createdDate;
	private int deliveryDays;
	private String farmerUsername;
	private String buyerAddressLine1;
	private String buyerAddressLine2;
	private String buyerAddressLine3;

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
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

	public String getShopLocation() {
		return shopLocation;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
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

	public String getFarmerUsername() {
		return farmerUsername;
	}

	public void setFarmerUsername(String farmerUsername) {
		this.farmerUsername = farmerUsername;
	}

	public String getBuyerAddressLine1() {
		return buyerAddressLine1;
	}

	public void setBuyerAddressLine1(String buyerAddressLine1) {
		this.buyerAddressLine1 = buyerAddressLine1;
	}

	public String getBuyerAddressLine2() {
		return buyerAddressLine2;
	}

	public void setBuyerAddressLine2(String buyerAddressLine2) {
		this.buyerAddressLine2 = buyerAddressLine2;
	}

	public String getBuyerAddressLine3() {
		return buyerAddressLine3;
	}

	public void setBuyerAddressLine3(String buyerAddressLine3) {
		this.buyerAddressLine3 = buyerAddressLine3;
	}

}
