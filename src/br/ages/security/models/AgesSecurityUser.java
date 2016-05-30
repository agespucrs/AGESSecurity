package br.ages.security.models;

import br.ages.security.interfaces.models.IAgesSecurityUser;
import br.ages.security.util.Util;

public class AgesSecurityUser implements IAgesSecurityUser {

	private String username;
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
		this.password = Util.encrytpPassword(password);
	}
}
