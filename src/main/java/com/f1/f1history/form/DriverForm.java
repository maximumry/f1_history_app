package com.f1.f1history.form;

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
	private String userId;
	
	@NotBlank
	private String driverId;
	
	@NotNull
	private String team;
	
	@NotBlank
	private String worldChampionships;
	
	@NotBlank
	private String wins;
	
	@NotBlank
	private String wikiUrl;

}
