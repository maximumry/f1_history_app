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

	//private static final String URL = 

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
			return new EventRestResult(90, errors, null);
		}
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Driver driver = modelMapper.map(form, Driver.class);
		driverService.insertDriver(driver);
		return new EventRestResult(0, null, null);
	}

	@GetMapping("/{driverId}")
	public List<Driver> getDriver(@PathVariable String driverId) {
		List<Driver> drivers = driverService.getDriver(driverId);
		for (int i = 0; i < drivers.size(); i++) {
			if (!(drivers.get(i).getDriverId().equals(driverId))) {
				drivers.remove(i);
			}
		}
		return drivers;
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
			return new EventRestResult(90, errors, null);
		}
		Driver driver = modelMapper.map(form, Driver.class);
		driverService.updateDriver(driver);
		return new EventRestResult(0, null, null);
	}

	@DeleteMapping("/{driverId}")
	public void deleteDriver(@PathVariable String driverId) {
		driverService.deleteDriver(driverId);
	}

}
