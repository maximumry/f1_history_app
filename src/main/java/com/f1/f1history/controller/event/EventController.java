package com.f1.f1history.controller.event;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.entity.Event;
import com.f1.f1history.exception.EventNotFoundException;
import com.f1.f1history.form.EventForm;
import com.f1.f1history.service.event.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
	
	private final EventService eventService;

	@GetMapping
	public List<Event> getAllEvent(){
		return eventService.getEventAll();
	}
	
	@PostMapping
	public void insertEvent(@RequestBody EventForm form) {
		eventService.insertEvent(form);
	}
	
	@GetMapping("/{eventId}")
	public Event getEvent(@PathVariable String eventId) {
		return eventService.getEvent(eventId).orElseThrow(
				() -> new EventNotFoundException(eventId));
	}
	
	@PutMapping("/{eventId}")
	public void updateEvent(@PathVariable String eventId,
			@RequestBody EventForm form) {
		eventService.updateEvent(eventId, form);
	}
	
	@DeleteMapping("/{eventId}")
	public void deleteEvent(@PathVariable String eventId) {
		eventService.deleteEvent(eventId);
	}
}
