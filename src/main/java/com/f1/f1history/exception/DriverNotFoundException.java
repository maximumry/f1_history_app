package com.f1.f1history.exception;

public class DriverNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public DriverNotFoundException(String driverId) {
		super(driverId + "は存在しません");
	}

}
