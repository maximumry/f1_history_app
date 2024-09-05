package com.f1.f1history.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("http://localhost:8080/auth/realms/user");

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.mvcMatchers("/login/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable();
		
		http.authorizeRequests(
				);
	}
	
}
