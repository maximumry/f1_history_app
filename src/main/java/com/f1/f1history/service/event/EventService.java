package com.f1.f1history.service.event;

import java.util.Map;
import java.util.Optional;

import com.f1.f1history.entity.Event;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface EventService {

	Optional<Event> getEvent(String eventId);

	Map<String, ObjectNode> filterRace(int yearDecade, String raceSearch);

	Map<String, ObjectNode> getSearchRace(int yearDecade, String raceSearch);
}
