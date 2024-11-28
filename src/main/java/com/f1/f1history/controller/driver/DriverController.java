package com.f1.f1history.controller.driver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.config.CustomUserDetails;
import com.f1.f1history.form.DriverForm;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

	@GetMapping
	public String getDrivers(@ModelAttribute DriverForm form,
			Model model,
			@AuthenticationPrincipal CustomUserDetails user) {
		String userId = user.getUserId();
		model.addAttribute("userId", userId);
		return "/driver/home";
	}

	@GetMapping("/{driverId}")
	public String getDriver(@PathVariable("driverId") String driverId, Model model) {
		model.addAttribute("driverId", driverId);
		return "/driver/driver-detail";
	}

	@GetMapping("/form")
	public String driverForm(@ModelAttribute DriverForm form) {
		return "/driver/driver-form";
	}

	@GetMapping("/{driverId}/edit")
	public String editDriver(@PathVariable("driverId") String driverId,
			@ModelAttribute DriverForm form,
			Model model) {
		model.addAttribute("driverId", driverId);
		return "/driver/driver-edit";
	}
}
