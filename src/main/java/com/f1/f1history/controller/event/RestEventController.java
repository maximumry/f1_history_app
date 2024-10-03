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
@RequestMapping("/event/api")
public class RestEventController {
	
	private final EventService eventService;

	@GetMapping
	public List<Event> getAllEvent(){
		return eventService.getEventAll();
	}
	
	@PostMapping
	public void insertEvent(@RequestBody EventForm form) {
		eventService.insertEvent(form);
	}
	
	@GetMapping("/{driverId}")
	public Event getEvent(@PathVariable String driverId) {
		return eventService.getEvent(driverId).orElseThrow(
				() -> new EventNotFoundException(driverId));
	}
	
	@PutMapping("/{driverId}")
	public void updateEvent(@PathVariable String driverId,
			@RequestBody EventForm form) {
		eventService.updateEvent(driverId, form);
	}
	
	@DeleteMapping("/{driverId}")
	public void deleteEvent(@PathVariable String driverId) {
		eventService.deleteEvent(driverId);
	}
}
