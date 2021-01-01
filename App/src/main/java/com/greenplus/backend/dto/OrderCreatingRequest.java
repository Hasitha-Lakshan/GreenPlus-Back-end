package com.greenplus.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderCreatingRequest {

	private int shopId;
	private String buyerUsername;
	private String deliveryAddressLine1;
	private String deliveryAddressLine2;
	private String deliveryAddressLine3;
	private String deliveryLocation;
	private String note;
	private String unit;
	private int quantity;
	private int totalPrice;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date createdDate;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date dueDate;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getBuyerUsername() {
		return buyerUsername;
	}

	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}

	public String getDeliveryAddressLine1() {
		return deliveryAddressLine1;
	}

	public void setDeliveryAddressLine1(String deliveryAddressLine1) {
		this.deliveryAddressLine1 = deliveryAddressLine1;
	}

	public String getDeliveryAddressLine2() {
		return deliveryAddressLine2;
	}

	public void setDeliveryAddressLine2(String deliveryAddressLine2) {
		this.deliveryAddressLine2 = deliveryAddressLine2;
	}

	public String getDeliveryAddressLine3() {
		return deliveryAddressLine3;
	}

	public void setDeliveryAddressLine3(String deliveryAddressLine3) {
		this.deliveryAddressLine3 = deliveryAddressLine3;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
