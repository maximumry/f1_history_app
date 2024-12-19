package com.f1.f1history.controller.driver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.service.driver.ChatGPTService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/driver")
public class RestDriverController {

	private final ChatGPTService chatGPTService;

	@GetMapping("/chatgpt")
	public String generateApiMessage(@RequestParam String userMessage) {
		String apiMessage = chatGPTService.generateAPIMessage(userMessage);
		return apiMessage;
	}

}
