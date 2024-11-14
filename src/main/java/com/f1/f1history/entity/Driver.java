package com.f1.f1history.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
	
	private String driverId;
	private String worldChampionships;
	private String wins;
	private String team;
	private String wikiUrl;
	private List<Event> eventList;
	private MUser user;
	private String userId;

}