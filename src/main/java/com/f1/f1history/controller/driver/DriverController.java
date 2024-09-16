package com.f1.f1history.controller.driver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/driver")
public class DriverController {
	
	@GetMapping
	public String getDrivers() {
		return "/driver/index";
	}

}
