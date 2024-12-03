package com.f1.f1history.service.driver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.f1.f1history.entity.Driver;

public interface DriverService {

	List<Driver> getAllDriver();

	void insertDriver(Driver driver);

	List<Driver> getDriver(String driverId);

	void updateDriver(Driver driver);

	void deleteDriver(String driverId);

	Optional<Driver> recommendDrivers(String driverId);

	Map<String, String> getByDecade();

}
