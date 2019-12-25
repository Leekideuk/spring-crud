package com.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.biz.user.UserService;
import com.common.validation.annotation.UserEmailUnique;

public class UserEmailUniqueValidator implements ConstraintValidator<UserEmailUnique, String> {
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ UserEmailUniqueValidator 클래스는 스프링 컨테이너에 빈 등록 안 했는데 DI 어떻게 가능???
	@Autowired
	private UserService userService;
	
	@Override
	public void initialize(UserEmailUnique userEmailUnique){}
	
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context){
		if(userService.existEmail(email) == true) return false;
		return true;
	}
}
