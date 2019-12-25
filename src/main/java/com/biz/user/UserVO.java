package com.biz.user;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.common.validation.annotation.UserEmailUnique;
import com.common.validation.annotation.UserIdUnique;

public class UserVO {	
	@UserIdUnique
	private String userId;
	@NotBlank
	private String password;
	private Date regDate;
	private int role;
	@Pattern(regexp="^[_0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
	@UserEmailUnique
	private String email;
	private String phone;
	private String emailAuthKey;
	private boolean emailCert;
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmailAuthKey() {
		return emailAuthKey;
	}
	public void setEmailAuthKey(String emailAuthKey) {
		this.emailAuthKey = emailAuthKey;
	}
	public boolean isEmailCert() {
		return emailCert;
	}
	public void setEmailCert(boolean emailCert) {
		this.emailCert = emailCert;
	}
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", password=" + password + ", regDate=" + regDate + ", role=" + role
				+ ", email=" + email + ", phone=" + phone + ", emailAuthKey=" + emailAuthKey + ", emailCert="
				+ emailCert + "]";
	}
}
