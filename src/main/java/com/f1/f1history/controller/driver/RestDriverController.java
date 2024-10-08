package com.f1.f1history.controller.driver;

import java.util.List;

import org.modelmapper.ModelMapper;
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
@RequestMapping("/driver/api")
@RequiredArgsConstructor
public class RestDriverController {
	
	private final DriverService driverService;
	private final ModelMapper modelMapper;
	
	@GetMapping
	public List<Driver> getDrivers(){
		return driverService.getAllDriver();
	}
	
	@PostMapping
	public void insertDriver(@RequestBody DriverForm form) {
		driverService.insertDriver(form);
	}
	
	@GetMapping("/{driverId}")
	public Driver getDriver(@PathVariable String driverId) {
		Driver driver = driverService.getDriver(driverId).orElseThrow(
				() -> new DriverNotFoundException(driverId));
		return driver;
	}
	
	@PutMapping("/{driverId}")
	public void updateDriver(@RequestBody DriverForm form,
			@PathVariable String driverId) {
		driverService.updateDriver(driverId, form);
	}
	
	@DeleteMapping("/{driverId}")
	public void deleteDriver(@PathVariable String driverId) {
		driverService.deleteDriver(driverId);
	}
	
	@GetMapping("/get/list")
	public List<Driver> getDriverList(DriverForm form){
		Driver driver = modelMapper.map(form, Driver.class);
		List<Driver> driverList = driverService.getDrivers(driver);
		return driverList;
	}

}
