package com.f1.f1history.service.driver;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.f1.f1history.dao.DriverInfoMapper;
import com.f1.f1history.entity.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

	private final DriverInfoMapper driverInfoMapper;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final JSONObject jsonObject;

	@Override
	public List<Driver> getAllDriver() {
		return driverInfoMapper.getAllDriver();
	}

	@Override
	public void insertDriver(Driver driver) {
		driverInfoMapper.insertDriver(driver);
	}

	int i = 0;

	@Override
	public Optional<Driver> getDriver(String driverId) {
		Driver drivers = recommendDriver();
		System.out.println(drivers);
		//		for (Driver driver : drivers) {
		//			System.out.println(driver + " " + i);
		//		}
		return driverInfoMapper.getDriver(driverId);
	}

	@Override
	public void updateDriver(Driver driver) {
		driverInfoMapper.updateDriver(driver);
	}

	@Override
	public void deleteDriver(String driverId) {
		driverInfoMapper.deleteDriver(driverId);
	}

	@Override
	public List<Driver> getDrivers(Driver driver) {
		List<Driver> testDriver = driverInfoMapper.searchDriver(driver);
		return testDriver;
	}

	public static final String URL = "http://ergast.com/api/f1/current/driverStandings.json";

	//リクエストされたドライバーに他のドライバーをおすすめに表示するためのメソッド
	private Driver recommendDriver() {
		try {
			String driver = restTemplate.getForObject(URL, String.class);
			System.out.println(objectMapper.readTree(driver));
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;

	}
}
