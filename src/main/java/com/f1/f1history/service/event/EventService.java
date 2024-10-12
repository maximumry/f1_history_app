package com.f1.f1history.service.event;

import java.util.List;
import java.util.Optional;

import com.f1.f1history.entity.Event;

public interface EventService {
	
	List<Event> getEventAll();
	
	void insertEvent(Event event);
	
	void updateEvent(Event event);
	
	Optional<Event> getEvent(String eventId);
	
	void deleteEvent(String driverId);

}
