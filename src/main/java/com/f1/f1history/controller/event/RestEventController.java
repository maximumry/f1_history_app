package com.f1.f1history.controller.event;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.service.event.EventService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/api")
public class RestEventController {

	private final EventService eventService;

	//required falseで空送信されてもエラーを防ぐ
	@GetMapping("/search")
	public Map<String, ObjectNode> getSearchRace(@RequestParam(required = false, defaultValue = "") int yearDecade,
			@RequestParam(required = false, defaultValue = "") String raceSearch) {
		return eventService.getSearchRace(yearDecade, raceSearch);
	}
}
