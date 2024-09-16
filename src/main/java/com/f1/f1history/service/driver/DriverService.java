package com.f1.f1history.service.driver;

import java.util.List;
import java.util.Optional;

import com.f1.f1history.entity.Driver;
import com.f1.f1history.form.DriverForm;

public interface DriverService {
	
	List<Driver> getAllDriver();
	
	void insertDriver(DriverForm form);
	
	Optional<Driver> getDriver(int driverId);
	
	void updateDriver(int driverId, DriverForm  form);
	
	void deleteDriver(int driverId);

}
