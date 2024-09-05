package com.f1.f1history.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventForm {
	//driverIdは仮実装
	private String driverId;
	
	@NotBlank
	private String description;
	
	@NotNull
	private LocalDate date;
	
	@NotBlank
	private String category;

}
