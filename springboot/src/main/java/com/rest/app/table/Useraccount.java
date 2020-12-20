/**
 * 
 */
package com.rest.app.table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author danielf
 *
 */
@Entity
@Table(name = "USERACCOUNT")
public class Useraccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private Long userid;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;

	@Override
	public String toString() {
		return "Usertable [userid=" + userid + ", username=" + username + ", password=" + password + ", name=" + name
				+ "]";
	}

	public Long getId() {
		return userid;
	}

	public void setId(Long userid) {
		this.userid = userid;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
