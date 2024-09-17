package com.f1.f1history.controller.driver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f1.f1history.form.DriverForm;

@Controller
@RequestMapping("/driver")
public class DriverController {
	
	@GetMapping
	public String getDrivers(@ModelAttribute DriverForm form) {
		return "/driver/index";
	}
	
	@GetMapping("/{driverId}")
	public String getDriver(@PathVariable("driverId") int driverId, Model model) {
		model.addAttribute("driverId", driverId);
		return "/driver/detail";
	}
}
