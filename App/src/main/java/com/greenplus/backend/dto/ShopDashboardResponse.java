package com.greenplus.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShopDashboardResponse {

	private int shopId;
	private String title;
	private boolean shopStatus;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date createdDate;

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(boolean shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	};

}
