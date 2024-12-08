package com.f1.f1history.service.event;

import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface EventService {

	Map<String, ObjectNode> filterRace(int yearDecade, String raceSearch);

	Map<String, ObjectNode> getSearchRace(int yearDecade, String raceSearch);
}
