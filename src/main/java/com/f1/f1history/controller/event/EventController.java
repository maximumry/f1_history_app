package com.f1.f1history.controller.event;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.form.CommentForm;
import com.f1.f1history.form.EventForm;

@Controller
@RequestMapping("/event")
public class EventController {

	@GetMapping("/form")
	public String form(@ModelAttribute EventForm eventForm) {
		return "/event/event-form";
	}

	@GetMapping("/{eventId}")
	public String getMethodName(@PathVariable("eventId") String eventId,
			@ModelAttribute CommentForm form,
			Model model) {
		String[] eventSplit = eventId.split("_");
		model.addAttribute("eventId", eventSplit);
		return "/event/event-detail";
	}

}
