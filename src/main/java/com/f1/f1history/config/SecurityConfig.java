package com.f1.f1history.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoderConfig passwordEncoderConfig;

//	//JWT発行者(issuer)であるKeycloakのURLをフォーマット(${ホスト名}/auth/realms/${realms名})に沿って指定する
//	@Autowired
//	CustomJwtConverter customJwtConverter;
//	
//	JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation("http://localhost:8080/auth/realms/user");
	
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
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.mvcMatchers("/login/**").authenticated()
				.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.failureUrl("/login?error")
				.usernameParameter("name")
				.passwordParameter("password")
				.loginPage("/login");
		//一時的に無効化
		http
			.csrf().disable();
		
//		//リクエスト制御
//		http.authorizeRequests(
//				requests -> requests
//					.antMatchers(HttpMethod.GET, "admin/hello").hasRole("user")
//					.antMatchers(HttpMethod.GET, "/user/hello").permitAll());
//		
//		http.oauth2ResourceServer(
//				oauth2ResourceServerCustomizer ->
//					oauth2ResourceServerCustomizer.jwt(jwtCustomizer ->
//							//2つの変換処理で利用するクラス(ConverterIF実装クラス/JwtDecoderIF実装クラス)にここでセットする
//							jwtCustomizer.jwtAuthenticationConverter((Converter<Jwt, ? extends AbstractAuthenticationToken>) customJwtConverter).decoder(jwtDecoder))
//		);
	}
	
}
