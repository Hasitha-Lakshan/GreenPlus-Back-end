package com.greenplus.backend.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private String title;
	@NotEmpty
	@Column
	private String note;
	@NotEmpty
	@Column
	private int quantity;
	@NotEmpty
	@Column
	private float price;
	@NotEmpty
	@Column
	private LocalDate createdDate;
	@NotEmpty
	@Column
	private LocalTime createdTime;
	@NotEmpty
	@Column
	private LocalTime duration;
	@NotEmpty
	@Column
	private boolean orderStatus;
}
