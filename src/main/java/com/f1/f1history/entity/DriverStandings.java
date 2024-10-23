package com.f1.f1history.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverStandings {

	private String position;
	private String positionText;
	private String points;
	private String wins;
	private Driver driver;
	//	private Constructors constructors;

}
