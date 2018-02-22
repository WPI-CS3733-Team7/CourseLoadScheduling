package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseLoad;

public class LoginUserReturnObject {
	
	private String message;
	private Integer userId;
	private String userRole;
	private List<Instructor> instructorList;
	private List<Course> courseList;
	private List<CourseLoad> courseLoadList;
	
	public LoginUserReturnObject(String message, Integer userId, String userRole, List<Instructor> instructorList, List<Course> courseList, List<CourseLoad> courseLoadList) {
		this.message = message;
		this.userId = userId;
		this.userRole = userRole;
		this.instructorList = instructorList;
		this.courseList = courseList;
		this.courseLoadList = courseLoadList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public List<Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(List<Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public List<CourseLoad> getCourseLoadList() {
		return courseLoadList;
	}

	public void setCourseLoadList(List<CourseLoad> courseLoadList) {
		this.courseLoadList = courseLoadList;
	}
}
