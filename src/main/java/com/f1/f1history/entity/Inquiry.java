package com.f1.f1history.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {

	private int inquiryId;
	private String inquirySelect;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime deletedAt;
	private String userId;
	private MUser user;

}
