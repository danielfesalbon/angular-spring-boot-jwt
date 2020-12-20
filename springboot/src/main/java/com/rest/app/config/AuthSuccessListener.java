package com.rest.app.config;
///**
// * 
// */
//package com.vpcommerce.app.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
///**
// * @author danielf
// *
// */
//@Component
//public class AuthSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
//
//	@Autowired
//	private AuthAttempRepository attemptService;
//
//	public void onApplicationEvent(AuthenticationSuccessEvent e) {
//		WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
//		attemptService.loginSucceeded(auth.getRemoteAddress());
//	}
//}