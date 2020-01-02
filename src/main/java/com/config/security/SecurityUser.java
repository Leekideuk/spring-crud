package com.config.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.biz.user.UserVO;

public class SecurityUser extends User{
	private static final long serialVersionUID = 1L;
	private boolean emailCert;
	private boolean enabled;
	
	public SecurityUser(UserVO vo){
		super(vo.getUserId(), vo.getPassword(), AuthorityUtils.createAuthorityList(vo.getRole().toString()));
		this.emailCert = vo.isEmailCert();
		this.enabled = vo.isEnabled();
	}

	public boolean isEmailCert() {
		return emailCert;
	}

	public void setEmailCert(boolean emailCert) {
		this.emailCert = emailCert;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
