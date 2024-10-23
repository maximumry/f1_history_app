package com.f1.f1history.controller.driver;

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

import com.f1.f1history.entity.Driver;
import com.f1.f1history.exception.DriverNotFoundException;
import com.f1.f1history.exception.EventRestResult;
import com.f1.f1history.form.DriverForm;
import com.f1.f1history.service.driver.DriverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/driver/api")
@RequiredArgsConstructor
public class RestDriverController {

	private final DriverService driverService;
	private final ModelMapper modelMapper;
	private final MessageSource messageSource;

	@GetMapping
	public List<Driver> getDrivers() {
		return driverService.getAllDriver();
	}

	@PostMapping
	public EventRestResult insertDriver(@Validated DriverForm form,
			BindingResult result,
			Locale locale) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();

			for (FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);

				errors.put(error.getField(), message);
			}
			return new EventRestResult(90, errors);
		}
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Driver driver = modelMapper.map(form, Driver.class);
		driverService.insertDriver(driver);
		return new EventRestResult(0, null);
	}

	@GetMapping("/{driverId}")
	public Driver getDriver(@PathVariable String driverId) {
		Driver driver = driverService.getDriver(driverId).orElseThrow(
				() -> new DriverNotFoundException(driverId));
		return driver;
	}

	@PutMapping("/{driverId}")
	public EventRestResult updateDriver(@PathVariable String driverId,
			@Validated DriverForm form,
			BindingResult result,
			Locale locale) {
		System.out.println(result.hasErrors());
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<String, String>();

			for (FieldError error : result.getFieldErrors()) {
				String message = messageSource.getMessage(error, locale);

				errors.put(error.getField(), message);
			}
			return new EventRestResult(90, errors);
		}
		Driver driver = modelMapper.map(form, Driver.class);
		driverService.updateDriver(driver);
		return new EventRestResult(0, null);
	}

	@DeleteMapping("/{driverId}")
	public void deleteDriver(@PathVariable String driverId) {
		driverService.deleteDriver(driverId);
	}

	@GetMapping("/get/list")
	public List<Driver> getDriverList(DriverForm form) {
		Driver driver = modelMapper.map(form, Driver.class);
		List<Driver> driverList = driverService.getDrivers(driver);
		return driverList;
	}

}
