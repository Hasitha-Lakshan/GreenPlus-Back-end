package com.greenplus.backend.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ProfilePictures")
public class ProfilePicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profilePictureId")
	private int profilePictureId;
	@NotEmpty
	@Column
	private String name;
	@NotEmpty
	@Column
	private String type;
	@NotEmpty
	@Column(name = "pictureBytes", length = 1000)
	private byte[] pictureBytes;

	//////////////////// Relationships/////////////////////////

	@OneToOne
	@JoinColumn(name = "userID")
	@JsonIgnore
	private User user;

	//////////////////////////////////////////////////////////

	public int getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(int profilePictureId) {
		this.profilePictureId = profilePictureId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ProfilePicture [profilePictureId=" + profilePictureId + ", name=" + name + ", type=" + type
				+ ", pictureBytes=" + Arrays.toString(pictureBytes) + ", user=" + user + "]";
	}

}
