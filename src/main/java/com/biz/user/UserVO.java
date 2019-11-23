package com.biz.user;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.common.annotation.UserIdUnique;

public class UserVO {
	@UserIdUnique
	private String userId;
	@NotEmpty
	private String password;
	private Date regDate;
	private int role;
	@Pattern(regexp="^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
	private String email;
	private String phone1;
	private String phone2;
	private String phone3;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", password=" + password + ", regDate=" + regDate + ", role=" + role
				+ ", email=" + email + ", phone1=" + phone1 + ", phone2=" + phone2 + ", phone3=" + phone3 + "]";
	}
	
	
}
