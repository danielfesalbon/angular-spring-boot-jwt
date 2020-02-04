/**
 * 
 */
package com.backend.service;

import java.util.Date;

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
				return ResponseEntity.ok(new AuthenticationResponse(jwt, jwtUtil.extractExpiratation(jwt)));
			}
			return ResponseEntity.ok(new ErrorResponse("Incorrect username or password"));

		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("Incorrect username or password");
		}
	}

	@Override
	@PostMapping("/tokenrefresh")
	public ResponseEntity<?> refreshToken(@RequestBody String token) {
		// TODO Auto-generated method stub
		try {
			System.out.println("method triggered..");
			if (token != null) {
				System.out.println("token found");
				String username = jwtUtil.extractUsername(token);
				final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				Date expiration = jwtUtil.extractExpiratation(token);
				long diff = expiration.getTime() - new Date().getTime();
				// long diffSeconds = diff / 1000 % 60;
				// long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				long diffDays = diff / (24 * 60 * 60 * 1000);
				System.out.println(diffDays + "day/s, " + diffHours + "hour/s");

				if (diffDays < 1 && diffHours < 2) {
					System.out.println("Token expiring for user " + username);
					// if expiring within 1 hr
					final String jwt = jwtUtil.generateToken(userDetails);
					System.out.println("Generating new token " + jwt);
					return ResponseEntity.ok(new AuthenticationResponse(jwt, jwtUtil.extractExpiratation(jwt)));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ErrorResponse("Error encountered"));
			// TODO: handle exception
		}
		return ResponseEntity.ok(new ErrorResponse("Error encountered"));
	}

}
