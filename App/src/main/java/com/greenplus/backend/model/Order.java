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

@Entity
@Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	@NotEmpty
	@Column
	private String shopTitle;
	@NotEmpty
	@Column(length = 300)
	private String note;
	@NotEmpty
	@Column
	private int quantity;
	@NotEmpty
	@Column
	private String unit;
	@NotEmpty
	@Column
	private float totalPrice;
	@NotEmpty
	@Column
	private Date createdDate;
	@NotEmpty
	@Column
	private Date dueDate;
	@NotEmpty
	@Column
	private Date completedDate;
	@NotEmpty
	@Column(columnDefinition = "enum('INPROGRESS','ACTIVE','COMPLETE','LATE')")
	private String orderStatus;
	@NotEmpty
	@Column
	private int deliveryDays;
	@NotEmpty
	@Column
	private String deliveryAddressLine1;
	@NotEmpty
	@Column
	private String deliveryAddressLine2;
	@NotEmpty
	@Column
	private String deliveryAddressLine3;
	@NotEmpty
	@Column
	private String deliveryLocation;

	//////////////////// Relationships/////////////////////////

	@ManyToOne
	@JoinColumn(name = "buyer")
	private User user;

	@ManyToOne
	@JoinColumn(name = "shop")
	private Shop shop;

	//////////////////////////////////////////////////////////

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getDeliveryDays() {
		return deliveryDays;
	}

	public void setDeliveryDays(int deliveryDays) {
		this.deliveryDays = deliveryDays;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", shopTitle=" + shopTitle + ", note=" + note + ", quantity=" + quantity
				+ ", unit=" + unit + ", totalPrice=" + totalPrice + ", createdDate=" + createdDate + ", dueDate="
				+ dueDate + ", completedDate=" + completedDate + ", orderStatus=" + orderStatus + ", deliveryDays="
				+ deliveryDays + ", deliveryAddressLine1=" + deliveryAddressLine1 + ", deliveryAddressLine2="
				+ deliveryAddressLine2 + ", deliveryAddressLine3=" + deliveryAddressLine3 + ", deliveryLocation="
				+ deliveryLocation + ", user=" + user + ", shop=" + shop + "]";
	}

}
