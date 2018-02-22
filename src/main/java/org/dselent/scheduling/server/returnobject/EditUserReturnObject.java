package org.dselent.scheduling.server.returnobject;

import java.util.List;

import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UsersRolesLink;

public class EditUserReturnObject
{
	List<User> userList;
	List<UsersRolesLink> userRoleLinkList;
	List<InstructorUserLink> instructorUserLinkList;
	List<Instructor> instructorList;
	
	public EditUserReturnObject(List<User> userList, List<UsersRolesLink> userRoleLinkList,
			List<InstructorUserLink> instructorUserLinkList, List<Instructor> instructorList) {
		super();
		this.userList = userList;
		this.userRoleLinkList = userRoleLinkList;
		this.instructorUserLinkList = instructorUserLinkList;
		this.instructorList = instructorList;
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
