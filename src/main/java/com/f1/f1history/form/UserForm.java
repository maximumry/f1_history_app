package com.f1.f1history.form;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
	
	private String name;
	private String password;
	private String role;
	private LocalDateTime createdAt;
	private String email;
}
