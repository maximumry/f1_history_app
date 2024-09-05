package com.f1.f1history.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DriverNotFoundExceptionControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(DriverNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String driverNotFoundHandler(DriverNotFoundException e) {
		return e.getMessage();
	}

}
