package com.greenplus.backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BuyerRequestDashboardResponse {

	private int buyerRequestId;
	private String title;
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
	private Date createdDate;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isBuyerRequestStatus() {
		return buyerRequestStatus;
	}

	public void setBuyerRequestStatus(boolean buyerRequestStatus) {
		this.buyerRequestStatus = buyerRequestStatus;
	}

}
