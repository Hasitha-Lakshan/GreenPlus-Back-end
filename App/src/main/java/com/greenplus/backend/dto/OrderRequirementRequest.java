package com.greenplus.backend.dto;

public class OrderRequirementRequest {

	private int shopId;
	private String buyerUsername;

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

}
