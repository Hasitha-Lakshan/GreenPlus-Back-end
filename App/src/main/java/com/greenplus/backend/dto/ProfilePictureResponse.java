package com.greenplus.backend.dto;

public class ProfilePictureResponse {

	private String name;
	private String type;
	private byte[] pictureBytes;

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

}
