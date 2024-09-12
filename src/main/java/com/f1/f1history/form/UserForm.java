package com.f1.f1history.form;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
	
	@NotBlank
	private String name;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
}
