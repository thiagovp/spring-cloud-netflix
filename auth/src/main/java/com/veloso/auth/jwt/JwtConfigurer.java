package com.veloso.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		var filter = new JwtTokenFilter(jwtTokenProvider);
		httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
