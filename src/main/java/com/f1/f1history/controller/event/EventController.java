package com.f1.f1history.controller.event;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.form.CommentForm;

@Controller
@RequestMapping("/event")
public class EventController {

	@GetMapping("/{eventId}")
	public String getMethodName(@PathVariable("eventId") String eventId,
			@AuthenticationPrincipal CustomUserDetails user,
			@ModelAttribute CommentForm form,
			Model model) {
		if (user == null) {
			String[] eventSplit = eventId.split("_");
			model.addAttribute("userId", null);
			model.addAttribute("eventId", eventSplit);
			return "/event/event-detail";
		}
		String[] eventSplit = eventId.split("_");
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("eventId", eventSplit);
		return "/event/event-detail";
	}

}
