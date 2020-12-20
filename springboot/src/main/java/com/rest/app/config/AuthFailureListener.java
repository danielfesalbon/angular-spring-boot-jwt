package com.rest.app.config;
///**
// * 
// */
//package com.vpcommerce.app.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
///**
// * @author danielf
// *
// */
//@Component
//public class AuthFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
//
//	@Autowired
//	private AuthAttempRepository attemptService;
//
//	private ObjectMapper mapper = new ObjectMapper();
//
//	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
//		WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
//		try {
//			System.out.println(mapper.writeValueAsString(auth));
//		} catch (JsonProcessingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		// attemptService.loginFailed(auth.getRemoteAddress());
//	}
//}