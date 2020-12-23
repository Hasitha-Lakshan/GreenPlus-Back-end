package com.greenplus.backend.dto;

public class ResetPasswordByUserRequest {

	private String oldPassword;
	private String userNewPassword;
	private String userNewconfirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUserNewPassword() {
		return userNewPassword;
	}

	public void setUserNewPassword(String userNewPassword) {
		this.userNewPassword = userNewPassword;
	}

	public String getUserNewconfirmPassword() {
		return userNewconfirmPassword;
	}

	public void setUserNewconfirmPassword(String userNewconfirmPassword) {
		this.userNewconfirmPassword = userNewconfirmPassword;
	}

}
