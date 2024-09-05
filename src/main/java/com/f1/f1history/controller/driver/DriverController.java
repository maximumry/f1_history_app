package com.f1.f1history.controller.driver;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f1.f1history.entity.Driver;
import com.f1.f1history.exception.DriverNotFoundException;
import com.f1.f1history.form.DriverForm;
import com.f1.f1history.service.driver.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
	
	private final DriverService driverService;
	
	@GetMapping
	public List<Driver> getDrivers(){
		return driverService.getAllDrivers();
	}
	
	@PostMapping
	public void insertDriver(@RequestBody DriverForm form) {
		driverService.insertDriver(form);
	}
	
	@GetMapping("/{driverId}")
	public Driver getDriver(@PathVariable int driverId) {
		return driverService.getDriver(driverId).orElseThrow(
				() -> new DriverNotFoundException(driverId));
	}
	
	@PutMapping("/{driverId}")
	public void updateDriver(@RequestBody DriverForm form,
			@PathVariable int driverId) {
		driverService.updateDriver(driverId, form);
	}
	
	@DeleteMapping("/{driverId}")
	public void deleteDriver(@PathVariable int driverId) {
		driverService.deleteDriver(driverId);
	}

}
