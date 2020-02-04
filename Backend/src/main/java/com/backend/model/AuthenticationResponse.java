package com.backend.model;

import java.util.Date;

public class AuthenticationResponse {

	private final String jwt;
	private Date expiration;

	public AuthenticationResponse(String jwt, Date expiration) {
		this.jwt = jwt;
		this.expiration = expiration;
	}

	public String getJwt() {
		return jwt;
	}

	public Date getExpiration() {
		return expiration;
	}

}
