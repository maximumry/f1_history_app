package com.f1.f1history.service.event;

import java.util.List;
import java.util.Optional;

import com.f1.f1history.entity.Event;
import com.f1.f1history.form.EventForm;

public interface EventService {
	
	List<Event> getEventAll();
	
	void insertEvent(EventForm form);
	
	void updateEvent(String driverId, EventForm form);
	
	Optional<Event> getEvent(String driverId);
	
	void deleteEvent(String driverId);

}
