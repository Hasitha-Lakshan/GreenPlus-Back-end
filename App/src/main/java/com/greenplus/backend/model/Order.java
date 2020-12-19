package com.greenplus.backend.model;

import java.util.Date;

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
	private Date createdDate;
	@NotEmpty
	@Column
	private Date dueDate;
	@NotEmpty
	@Column
	private boolean orderStatus;
}
