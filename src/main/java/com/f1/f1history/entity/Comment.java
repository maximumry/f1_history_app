package com.f1.f1history.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	private int commentId;
	private String content;
	private int userId;
	private String eventId;
	private LocalDateTime createdAt;
	private MUser user;
	private Event event;

}
