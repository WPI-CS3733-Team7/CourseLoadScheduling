package org.dselent.scheduling.server.returnobject;

public class ChangePasswordReturnObject {

	private String message;
	
	public ChangePasswordReturnObject(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
