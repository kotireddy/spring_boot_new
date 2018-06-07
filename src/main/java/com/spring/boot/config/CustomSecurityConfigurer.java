package com.spring.boot.config;

import com.spring.boot.handler.CustomAuthenticationSuccessHandler;
import com.spring.boot.handler.CustomFailureHandler;
import com.spring.boot.handler.CustomLogoutHandler;
import com.spring.boot.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class CustomSecurityConfigurer extends WebSecurityConfigurerAdapter{

	private static final Logger LOGGER = LoggerFactory.
										getLogger(CustomSecurityConfigurer.class);
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;

	@Autowired
	private CustomFailureHandler failureHandler;

	@Autowired
	private CustomLogoutHandler logoutSuccessHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("<<<<<<< AuthenticationManagerBuilder Configuration >>>>>>>");
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(getPasswordEncoder());
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("<<<<<<< Http Security Configuration >>>>>>>");
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/","/home").permitAll()
			.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
				.successHandler(successHandler)
				.failureHandler(failureHandler)
		.and()
		.logout().permitAll()
			.logoutSuccessHandler(logoutSuccessHandler)
		.and()
		.exceptionHandling().accessDeniedPage("/403");
	}
	
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return true;
			}
			
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}
		};
	}


}
