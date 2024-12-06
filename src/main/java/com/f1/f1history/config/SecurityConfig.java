package com.f1.f1history.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	public SecurityConfig(@Lazy UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
				.ignoring()
				.antMatchers("/webjars/**")
				.antMatchers("/css/**")
				.antMatchers("/js/**")
				.antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.authorizeRequests()
				.antMatchers("/user/login").permitAll()
				.antMatchers("/user/signup").permitAll()
				.antMatchers("/driver").permitAll()
				.antMatchers("/comment/**").permitAll()
				.antMatchers("/event/**").permitAll()
				.antMatchers("/admin").hasAuthority("ADMIN")
				.antMatchers("/inquiry").hasAuthority("ADMIN")
				.anyRequest().authenticated();

		http
				.formLogin()
				.loginProcessingUrl("/user/login")
				.loginPage("/user/login")
				.failureUrl("/login?error")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/driver", true);

		http
				.logout()
				.logoutUrl("/user/logout")
				.logoutSuccessUrl("/login?logout");

		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = passwordEncoder();
		//ユーザーデータで認証
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(encoder);
	}

}
