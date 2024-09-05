package com.f1.f1history.exception;

public class EventNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public EventNotFoundException(String eventId) {
		super(eventId + "は存在しません");
	}

}
