/**
 * 
 */
package com.backend.service;

import org.springframework.http.ResponseEntity;

import com.backend.model.AuthenticationRequest;

/**
 * @author Daniel Fesalbon
 *
 */
public interface SecurityService {

	ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) throws Exception;

	ResponseEntity<?> refreshToken(String token);

}
