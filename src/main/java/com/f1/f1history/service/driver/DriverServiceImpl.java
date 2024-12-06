package com.f1.f1history.service.driver;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

	@Override
	public Map<String, String> getByDecade() {
		Map<String, String> yearDecade = new HashMap<String, String>();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		double past = year - 1949;
		double result = Math.ceil(past / 10);
		int displayDecade = 1950;

		for (int i = 0; i < result; i++) {
			String iString = String.valueOf(i);
			yearDecade.put(iString, displayDecade + "年〜" + (displayDecade + 10) + "年");
			displayDecade += 10;
		}

		return yearDecade;
	}
}
