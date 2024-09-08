package com.f1.f1history.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
	
	public PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

}
