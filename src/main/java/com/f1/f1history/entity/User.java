package com.f1.f1history.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private int userId;
	private String name;
	private String password;
	private String role;
	private LocalDateTime createdAt;
	private String email;
	private List<Driver> driverList;
	private List<Event> eventList;
}
