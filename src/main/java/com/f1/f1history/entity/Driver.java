package com.f1.f1history.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
	
	private String driverId;
	private String name;
	private String country;
	private LocalDate dateOfBirth;
	private String placeOfBirth;
	private List<Event> eventList;

}