package com.controller.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	private MessageSource messageSource;
	 
    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
	
    // BoardServiceImpl.updateBoard() 트랜잭션 예외 테스트.
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseEntity<String> exceptionHandler(Exception exception){
		return new ResponseEntity<String>("transaction exception test", HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	// Validator 예외 처리.
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> exceptionHandler(MethodArgumentNotValidException e){
		Map<String,Object> map =  new HashMap<String, Object>();
		BindingResult brs = e.getBindingResult();
		
		List<FieldError> errors = brs.getFieldErrors();
		for (FieldError error : errors ) {
			map.put(error.getField(), messageSource.getMessage(error, null));
		}
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
	}
	
}
