package com.f1.f1history.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.f1.f1history.entity.Driver;
import com.f1.f1history.form.DriverForm;

@Mapper
public interface DriverInfoMapper {
	
	List<Driver> getAllDriver();
	
	void insertDriver(DriverForm form);
	
	Optional<Driver> getDriver(int driverId);
	
	void updateDriver(int driverId,
			@Param("name") String name,
			@Param("country") String country,
			@Param("dateOfBirth") LocalDate dateOfBirth,
			@Param("placeOfBirth") String placeOfBirth);
	
	void deleteDriver(int driverId);

	List<Driver> searchDriver(Driver driver);

}
