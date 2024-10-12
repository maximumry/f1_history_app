package com.f1.f1history.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventForm {
	@NotBlank
	private String driverId;
	
	@NotBlank
	private String userId;
	
	@NotBlank
	private String description;
	
	@NotNull(message = "イベントの日付を入力してください")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotBlank
	private String category;
	
	@NotBlank
	private String weatherCondition;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String youtubeUrl;

}
