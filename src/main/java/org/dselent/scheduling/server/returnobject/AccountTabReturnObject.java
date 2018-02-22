package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UsersRolesLink;

import java.util.List;

public class AccountTabReturnObject {
    String firstName;
    String lastName;
    String userName;
    String userRole;
    String email;
    List<User> userList;
    List<UsersRolesLink> userRoleLinkList;
    List<InstructorUserLink> instructorUserLinkList;
    List<Instructor> instructorList;

	public AccountTabReturnObject(String firstName, String lastName, String userName, String userRole, String email,
			List<User> userList, List<UsersRolesLink> userRoleLinkList, List<InstructorUserLink> instructorUserLinkList,
			List<Instructor> instructorList) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.userRole = userRole;
		this.email = email;
		this.userList = userList;
		this.userRoleLinkList = userRoleLinkList;
		this.instructorUserLinkList = instructorUserLinkList;
		this.instructorList = instructorList;
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



	public String getUserRole() {
		return userRole;
	}



	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public List<User> getUserList() {
		return userList;
	}



	public void setUserList(List<User> userList) {
		this.userList = userList;
	}



	public List<UsersRolesLink> getUserRoleLinkList() {
		return userRoleLinkList;
	}



	public void setUserRoleLinkList(List<UsersRolesLink> userRoleLinkList) {
		this.userRoleLinkList = userRoleLinkList;
	}



	public List<InstructorUserLink> getInstructorUserLinkList() {
		return instructorUserLinkList;
	}



	public void setInstructorUserLinkList(List<InstructorUserLink> instructorUserLinkList) {
		this.instructorUserLinkList = instructorUserLinkList;
	}



	public List<Instructor> getInstructorList() {
		return instructorList;
	}



	public void setInstructorList(List<Instructor> instructorList) {
		this.instructorList = instructorList;
	}
}
