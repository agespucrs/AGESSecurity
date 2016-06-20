package br.ages.security.models;

import br.ages.security.interfaces.models.IAgesSecurityResult;

public class AgesSecurityResult implements IAgesSecurityResult {

	private Boolean isSucceeded;
	private String message;
	private AgesSecurityStatus status;
	
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
	
	public void setStatus(AgesSecurityStatus status) {
		this.status = status;
	}
	
	public AgesSecurityStatus getStatus() {
		return status;
	}
	
	public enum AgesSecurityStatus {
		INVALID_DATA, CLASS_NOT_FOUND, DATABASE_CONNECTION_ERROR, UNEXPECTED_ERROR, OPERATION_SUCCESS, DATA_ALREADY_EXISTS, DATA_NOT_EXISTS  
	}
}
