package com.f1.f1history.service.driver;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.f1.f1history.dao.DriverInfoMapper;
import com.f1.f1history.entity.Driver;
import com.f1.f1history.form.DriverForm;

@Service
@Transactional
public class DriverServiceImpl implements DriverService {
	
	@Autowired
	private DriverInfoMapper driverInfoMapper;

	@Override
	public List<Driver> getAllDriver() {
		return driverInfoMapper.getAllDriver();
	}
	
	@Override
	public void insertDriver(DriverForm form) {
		driverInfoMapper.insertDriver(form);
	}

	@Override
	public Optional<Driver> getDriver(int driverId) {
		return driverInfoMapper.getDriver(driverId);
	}
	
	@Override
	public void updateDriver(int driverId, DriverForm form) {
		driverInfoMapper.updateDriver(driverId, form.getName(), form.getCountry(), form.getDateOfBirth(), form.getPlaceOfBirth());
	}
	
	@Override
	public void deleteDriver(int driverId) {
		driverInfoMapper.deleteDriver(driverId);
	}

	@Override
	public List<Driver> getDrivers(Driver driver) {
		List<Driver> testDriver = driverInfoMapper.searchDriver(driver);
		for(Driver test : testDriver) {
		System.out.println(test + "aaaa");
		}
		return testDriver;
	}
}
