package com.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.service.UserSecurityService;
import com.backend.util.JWTRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserSecurityService userDetailsService;

	@Autowired
	private JWTRequestFilter requestFiter;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/security/authenticate").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(requestFiter, UsernamePasswordAuthenticationFilter.class);
		// http.addFilterBefore(corsFilter(), SessionManagementFilter.class); // adds
		// your custom CorsFilter
	}

//	@Bean
//	CorsFilter corsFilter() {
//		CorsFilter filter = new CorsFilter();
//		return filter;
//	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

}
