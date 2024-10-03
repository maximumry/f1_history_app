package com.f1.f1history.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	
	private int eventId;
	private String driverId;
	private String description;
	private LocalDate date;
	private String category;
	private String weatherCondition;
	private String user_id;
	private String title;
	private String youtubeUrl;
	private Driver driver;
	private User user;

}
