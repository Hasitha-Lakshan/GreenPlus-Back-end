package com.greenplus.backend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Shops")
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shopId;
	@NotEmpty
	@Column
	private String title;
	@NotEmpty
	@Column(length = 700)
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
	private float priceOfOneUnit;
	@NotEmpty
	@Column
	private String location;
	@NotEmpty
	@Column
	private Date createdDate;
	@NotEmpty
	@Column
	private boolean shopStatus;
	@NotEmpty
	@Column
	private int deliveryDays;

	//////////////////// Relationships/////////////////////////

	@ManyToOne
	@JoinColumn(name = "userID")
	@JsonIgnore
	private User user;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "shop")
	private ShopPicture shopPicture;
	
	//////////////////////////////////////////////////////////

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(boolean shopStatus) {
		this.shopStatus = shopStatus;
	}

	public int getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ShopPicture getShopPicture() {
		return shopPicture;
	}

	public void setShopPicture(ShopPicture shopPicture) {
		this.shopPicture = shopPicture;
	}

	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", title=" + title + ", description=" + description + ", category=" + category
				+ ", subCategory=" + subCategory + ", unit=" + unit + ", priceOfOneUnit=" + priceOfOneUnit
				+ ", location=" + location + ", createdDate=" + createdDate + ", shopStatus=" + shopStatus
				+ ", deliveryDays=" + deliveryDays + ", user=" + user + ", shopPicture=" + shopPicture + "]";
	}

}
