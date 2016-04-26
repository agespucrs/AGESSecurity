package br.ages.security.models;

import br.ages.security.interfaces.models.IAgesSecurityResult;

public class AgesSecurityResult implements IAgesSecurityResult {

	private Boolean isSucceeded;
	private String message;
	
	public AgesSecurityResult() {
		isSucceeded = false;
	}

	public Boolean isSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
