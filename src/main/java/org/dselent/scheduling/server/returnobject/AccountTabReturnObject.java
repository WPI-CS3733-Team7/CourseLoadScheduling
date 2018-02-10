package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.User;

import java.util.List;

public class AccountTabReturnObject {
    String firstName;
    String lastName;
    String userName;
    Integer userState;
    String Email;
    String password;
    List<User> userList;

    public AccountTabReturnObject(String firstName, String lastName, String userName, Integer userState, String Email, String password, List<User> userList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userState = userState;
        this.Email = Email;
        this.password = password;
        this.userList = userList;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
