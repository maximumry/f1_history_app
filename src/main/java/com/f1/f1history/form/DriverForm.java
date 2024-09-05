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
public class DriverForm {
	
	@NotNull
	private String name;
	
	@NotBlank
	private String country;
	
	@NotNull
	private LocalDate dateOfBirth;
	
	@NotBlank
	private String placeOfBirth;
	
//	private List<Event> eventId;

}
