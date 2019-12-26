package com.common.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.biz.user.UserService;
import com.biz.user.UserVO;

public class UpdateUserEmailValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return UserVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
	
	public void validate(Object target, Errors errors, UserService userService) {
		UserVO user = (UserVO) target;
		if(userService.existEmail(user.getEmail())){
			// email == key / UpdateUserEmailValidator.email.duplication == messageSource에 저장된 key
			errors.rejectValue("email", "UpdateUserEmailValidator.email.duplication");
		}
	}

}
