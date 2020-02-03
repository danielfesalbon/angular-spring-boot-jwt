/**
 * 
 */
package com.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Daniel Fesalbon
 *
 */
@Entity
@Table(name = "USERTABLE")
public class Usertable {

	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
