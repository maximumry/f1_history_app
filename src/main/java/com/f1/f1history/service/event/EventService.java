package com.f1.f1history.service.event;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.f1.f1history.entity.Event;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface EventService {

	List<Event> getEventAll();

	void insertEvent(Event event);

	void updateEvent(Event event);

	Optional<Event> getEvent(String eventId);

	void deleteEvent(Event event);

	Map<String, ObjectNode> getSearchRace(String raceSearch);

	Map<String, ObjectNode> filterRace(String raceSearch);
}
