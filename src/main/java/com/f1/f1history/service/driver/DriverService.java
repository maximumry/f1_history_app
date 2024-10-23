package com.f1.f1history.service.driver;

import java.util.List;
import java.util.Optional;

import com.f1.f1history.entity.Driver;

public interface DriverService {

	List<Driver> getAllDriver();

	void insertDriver(Driver driver);

	Optional<Driver> getDriver(String driverId);

	void updateDriver(Driver driver);

	void deleteDriver(String driverId);

	List<Driver> getDrivers(Driver driver);

}
