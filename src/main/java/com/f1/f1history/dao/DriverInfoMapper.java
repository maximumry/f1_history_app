package com.f1.f1history.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.f1.f1history.entity.Driver;

@Mapper
public interface DriverInfoMapper {

	List<Driver> getAllDriver();

	void insertDriver(Driver driver);

	Optional<Driver> getDriver(String driverId);

	void updateDriver(@Param("driver") Driver driver);

	void deleteDriver(@Param("driverId") String driverId);

	List<Driver> searchDriver(Driver driver);

}
