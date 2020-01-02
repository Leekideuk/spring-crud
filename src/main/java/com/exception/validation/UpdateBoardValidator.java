package com.exception.validation;

import java.util.HashMap;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 게시글 변경
public class UpdateBoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return HashMap.class.equals(clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(Object target, Errors errors) {
		HashMap<String, Object> map = (HashMap<String, Object>) target;		
		String title = (String) map.get("title");
		String content = (String) map.get("content");

		if(title == null || title.trim().isEmpty()){
			errors.rejectValue("title", "UpdateBoardValidator.title.blank");
		}
		if(content == null || content.trim().isEmpty()){
			errors.rejectValue("content", "UpdateBoardValidator.content.blank");
		}
	}
}
