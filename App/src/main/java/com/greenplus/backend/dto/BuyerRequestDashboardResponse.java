package com.greenplus.backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BuyerRequestDashboardResponse {
	
	private int buyerRequestId;
	private String title;
	private LocalDate createdDate;
	private LocalTime createdTime;
	private boolean buyerRequestStatus;

	public int getBuyerRequestId() {
		return buyerRequestId;
	}

	public void setBuyerRequestId(int buyerRequestId) {
		this.buyerRequestId = buyerRequestId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalTime createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isBuyerRequestStatus() {
		return buyerRequestStatus;
	}

	public void setBuyerRequestStatus(boolean buyerRequestStatus) {
		this.buyerRequestStatus = buyerRequestStatus;
	}

}
