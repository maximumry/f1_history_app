package com.f1.f1history.service.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.f1.f1history.dao.DriverInfoMapper;
import com.f1.f1history.entity.Driver;
import com.f1.f1history.exception.DriverNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

	private final DriverInfoMapper driverInfoMapper;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public List<Driver> getAllDriver() {
		return driverInfoMapper.getAllDriver();
	}

	@Override
	public void insertDriver(Driver driver) {
		driverInfoMapper.insertDriver(driver);
	}

	@Override
	public List<Driver> getDriver(String driverId) {
		List<Driver> recommendDrivers = recommendDriver(driverId);
		recommendDrivers.add(driverInfoMapper.getDriver(driverId).orElseThrow(
				() -> new DriverNotFoundException(driverId)));
		return recommendDrivers;
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
	public Optional<Driver> recommendDrivers(String driverId) {
		return driverInfoMapper.getDriver(driverId);
	}

	public static final String URL = "http://ergast.com/api/f1/current/driverStandings.json";

	//リクエストされたドライバーに他のドライバーをおすすめに表示するためのメソッド
	//hashmapにドライバーidとポイントを挿入してポイント差によってレコメンドを変える
	private List<Driver> recommendDriver(String driverId) {
		List<Integer> driverPotition = new ArrayList<Integer>();
		List<String> driverName = new ArrayList<String>();
		Map<String, Integer> driverInfo = new HashMap<String, Integer>();
		List<Driver> driverHold = new ArrayList<Driver>();
		try {
			String driverUrl = restTemplate.getForObject(URL, String.class);
			JsonNode jsonNode = objectMapper.readTree(driverUrl);
			JsonNode driverStandings = jsonNode.get("MRData")
					.get("StandingsTable")
					.get("StandingsLists")
					.get(0)
					.get("DriverStandings");
			for (int i = 0; i < driverStandings.size(); i++) {
				driverPotition.add(driverStandings.get(i).get("position").asInt());
				driverName.add(driverStandings.get(i).get("Driver").get("driverId").textValue());
				driverInfo.put(driverName.get(i), driverStandings.get(i).get("points").asInt());
			}
			int scoreGap = 0;
			int index = 0;
			for (int i = 0; i < driverName.size(); i++) {
				String name = driverName.get(i);
				if (!(driverId.equals(name)) && index == 0) {
					scoreGap = driverInfo.get(name) - driverInfo.get(driverId);
					if (scoreGap <= 50) {
						Driver driver = driverInfoMapper.getDriver(name).orElseThrow(
								() -> new DriverNotFoundException(name));
						driverHold.add(driver);
					}
				} else if (!(driverId.equals(name)) && index != 0) {
					scoreGap = driverInfo.get(driverId) - driverInfo.get(name);
					if (scoreGap <= 50) {
						Driver driver = recommendDrivers(name).orElseThrow(
								() -> new DriverNotFoundException(name));
						driverHold.add(driver);
					}
				} else if (driverId.equals(driverName.get(i))) {
					index = 1;
				}
				scoreGap = 0;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return driverHold;

	}
}
