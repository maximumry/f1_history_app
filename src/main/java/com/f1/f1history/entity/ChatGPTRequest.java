package com.f1.f1history.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChatGPTRequest {

	private String model;
	private List<Message> messages;

	@Data
	@AllArgsConstructor
	public class Message {
		private String role;
		private String content;
	}

}
