package com.f1.f1history.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {
	
	private String inquiryId;
	private String inquirySelect;
	private String name;
	private String email;
	private String content;
	private LocalDateTime createdAt;
	private int userId;
	private User user;

}
