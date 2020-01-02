package com.exception.validation.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.biz.user.UserService;
import com.exception.validation.annotation.UserIdUnique;

public class UserIdUniqueValidator implements ConstraintValidator<UserIdUnique, String> {
	@Autowired
	private UserService userService;
	
	@Override
	public void initialize(UserIdUnique userIdUnique) {}
	
	@Override
	public boolean isValid(String userId, ConstraintValidatorContext context) {
		if( userService.existUserId(userId) == true){ return false;}
		return true;
	}

}
