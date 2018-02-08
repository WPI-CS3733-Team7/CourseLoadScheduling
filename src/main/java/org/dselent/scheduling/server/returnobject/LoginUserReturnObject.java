package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.Course;

public class LoginUserReturnObject {
	
	private String message;
	private Integer userId;
	private List<Instructor> instructorList;
	private List<Course> courseList;
	
	public LoginUserReturnObject(String message, Integer userId, List<Instructor> instructorList, List<Course> courseList) {
		this.message = message;
		this.userId = userId;
		this.instructorList = instructorList;
		this.courseList = courseList;
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
}
