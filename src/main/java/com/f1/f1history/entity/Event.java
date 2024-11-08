package com.f1.f1history.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private String eventId;
	private String driverId;
	private String description;
	private LocalDate date;
	private String category;
	private String weatherCondition;
	private String userId;
	private String title;
	private String youtubeUrl;
	private Driver driver;
	private User user;
	private List<Comment> commentList;

}
