package com.f1.f1history.controller.event;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.entity.Event;
import com.f1.f1history.exception.EventNotFoundException;
import com.f1.f1history.exception.EventRestResult;
import com.f1.f1history.form.EventForm;
import com.f1.f1history.service.event.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/api")
public class RestEventController {
	
	private final EventService eventService;
	private final ModelMapper modelMapper;
	private final MessageSource messageSource;
	

	@GetMapping
	public List<Event> getAllEvent(){
		return eventService.getEventAll();
	}
	
	@PostMapping
	public EventRestResult insertEvent(@Validated EventForm form,
			BindingResult result,
			Locale locale) {
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			//エラーメッセージを取得
			for(FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			//エラー結果の返却
			return new EventRestResult(90, errors);
		}
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Event event = modelMapper.map(form, Event.class);
		eventService.insertEvent(event);
		return new EventRestResult(0, null);
	}
	
	@GetMapping("/{eventId}")
	public Event getEvent(@PathVariable String eventId) {
		return eventService.getEvent(eventId).orElseThrow(
				() -> new EventNotFoundException(eventId));
	}
	
	@PutMapping("/{eventId}")
	public EventRestResult updateEvent(@PathVariable String eventId,
			@Validated EventForm form,
			BindingResult result,
			Locale locale) {
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();
			
			for(FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			return new EventRestResult(90, errors);
		}
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Event event = modelMapper.map(form, Event.class);
		event.setEventId(eventId);
		eventService.updateEvent(event);
		return new EventRestResult(0, null);
	}
	
	@DeleteMapping("/{eventId}")
	public EventRestResult deleteEvent(@PathVariable String eventId,
			@Validated EventForm form,
			BindingResult result,
			Locale locale) {
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();
			
			for(FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);
				errors.put(error.getField(), message);
			}
			return new EventRestResult(90, errors);
		}
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Event event = modelMapper.map(form, Event.class);
		event.setEventId(eventId);
		eventService.deleteEvent(event);
		return new EventRestResult(0, null);
	}
}
