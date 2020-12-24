package com.greenplus.backend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BuyerRequests")
public class BuyerRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int buyerRequestId;
	@NotEmpty
	@Column
	private String title;
	@NotEmpty
	@Column(length = 350)
	private String description;
	@NotEmpty
	@Column
	private String category;
	@NotEmpty
	@Column
	private String subCategory;
	@NotEmpty
	@Column
	private String unit;
	@NotEmpty
	@Column
	private int quantity;
	@NotEmpty
	@Column
	private float price;
	@NotEmpty
	@Column
	private String location;
	@NotEmpty
	@Column
	private Date createdDate;
	@NotEmpty
	@Column
	private boolean buyerRequestStatus;
	@NotEmpty
	@Column
	private int expectDays;

	//////////////////// Relationships/////////////////////////

	@ManyToOne
	@JoinColumn(name = "userID")
	@JsonIgnore
	private User user;

	//////////////////////////////////////////////////////////

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

	public int getExpectDays() {
		return expectDays;
	}

	public void setExpectDays(int expectDays) {
		this.expectDays = expectDays;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "BuyerRequest [buyerRequestId=" + buyerRequestId + ", title=" + title + ", description=" + description
				+ ", category=" + category + ", subCategory=" + subCategory + ", unit=" + unit + ", quantity="
				+ quantity + ", price=" + price + ", location=" + location + ", createdDate=" + createdDate
				+ ", buyerRequestStatus=" + buyerRequestStatus + ", expectDays=" + expectDays + ", user=" + user + "]";
	}

}
