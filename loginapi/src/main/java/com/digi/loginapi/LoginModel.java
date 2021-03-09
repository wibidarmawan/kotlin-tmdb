package com.digi.loginapi;

public class LoginModel {
	private String status;
	private UserModel userModelList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UserModel getUserModelList() {
		return userModelList;
	}
	public void setUserModelList(UserModel userModelList) {
		this.userModelList = userModelList;
	}
	
	
}
