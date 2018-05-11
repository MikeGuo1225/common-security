/**
 * 
 */
package com.chentongwe.entity;

import java.util.Date;
import java.util.List;

public class User {
	private String id;
	private String username;
	private String password;
	private Date birthday;

	private List<String> authorizes;

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
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<String> getAuthorizes() {
		return authorizes;
	}

	public void setAuthorizes(List<String> authorizes) {
		this.authorizes = authorizes;
	}
}
