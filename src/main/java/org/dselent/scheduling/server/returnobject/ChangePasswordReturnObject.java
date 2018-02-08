package org.dselent.scheduling.server.returnobject;

import java.util.List;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;

public class ChangePasswordReturnObject {

	private String newPassword;
	private String message;
	
	public ChangePasswordReturnObject(String newPassword, String message) {
		this.newPassword = newPassword;
		this.message = message;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
