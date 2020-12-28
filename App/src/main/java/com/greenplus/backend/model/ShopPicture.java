package com.greenplus.backend.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ShopPictures")
public class ShopPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopPictureId")
	private int shopPictureId;
	@NotEmpty
	@Column
	private String name;
	@NotEmpty
	@Column
	private String type;
	@Lob
	@NotEmpty
	@Column(name = "pictureBytes")
	private byte[] pictureBytes;

	//////////////////// Relationships/////////////////////////

	@OneToOne
	@JoinColumn(name = "shop")
	private Shop shop;

	//////////////////////////////////////////////////////////

	public int getShopPictureId() {
		return shopPictureId;
	}

	public void setShopPictureId(int shopPictureId) {
		this.shopPictureId = shopPictureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPictureBytes() {
		return pictureBytes;
	}

	public void setPictureBytes(byte[] pictureBytes) {
		this.pictureBytes = pictureBytes;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	public String toString() {
		return "ShopPicture [shopPictureId=" + shopPictureId + ", name=" + name + ", type=" + type + ", pictureBytes="
				+ Arrays.toString(pictureBytes) + ", shop=" + shop + "]";
	}

}
