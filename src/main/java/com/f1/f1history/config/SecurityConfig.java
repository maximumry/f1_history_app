package com.f1.f1history.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

import com.f1.f1history.security.CustomJwtConverter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//JWT発行者(issuer)であるKeycloakのURLをフォーマット(${ホスト名}/auth/realms/${realms名})に沿って指定する
	@Autowired
	CustomJwtConverter customJwtConverter;
	
	JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("http://localhost:8080/auth/realms/user");

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.mvcMatchers("/login/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable();
		
		//リクエスト制御
		http.authorizeRequests(
				requests -> requests
					.antMatchers(HttpMethod.GET, "admin/hello").hasRole("user")
					.antMatchers(HttpMethod.GET, "/user/hello").permitAll());
		
		http.oauth2ResourceServer(
				oauth2ResourceServerCustomizer ->
					oauth2ResourceServerCustomizer.jwt(jwtCustomizer ->
							//2つの変換処理で利用するクラス(ConverterIF実装クラス/JwtDecoderIF実装クラス)にここでセットする
							jwtCustomizer.jwtAuthenticationConverter((Converter<Jwt, ? extends AbstractAuthenticationToken>) customJwtConverter).decoder(jwtDecoder))
		);
	}
	
}
