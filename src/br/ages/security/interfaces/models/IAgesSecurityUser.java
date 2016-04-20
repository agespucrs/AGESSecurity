package br.ages.security.interfaces.models;

public interface IAgesSecurityUser {
	
	String getUsername();
	void setUsername(String username);
	
	String getPassword();
	void setPassword(String password);
}
