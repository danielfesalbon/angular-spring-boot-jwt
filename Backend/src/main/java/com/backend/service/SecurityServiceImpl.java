/**
 * 
 */
package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.AuthenticationRequest;
import com.backend.model.AuthenticationResponse;
import com.backend.model.ErrorResponse;
import com.backend.util.JWTUtil;

/**
 * @author Daniel Fesalbon
 *
 */
@Component
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/security")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private UserSecurityService userDetailsService;
	@Autowired
	private JWTUtil jwtUtil;

	@Override
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			if (userDetails.getPassword().equals(authenticationRequest.getPassword())) {
				final String jwt = jwtUtil.generateToken(userDetails);
				return ResponseEntity.ok(new AuthenticationResponse(jwt));
			}
			return ResponseEntity.ok(new ErrorResponse("Incorrect username or password"));

		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("Incorrect username or password");
		}
	}
	
	


}
