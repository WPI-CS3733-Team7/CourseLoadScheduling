package org.dselent.scheduling.server.returnobject;

import java.util.List;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;

public class ValidateReturnObject {

	private String message;
	private List<Instructor> instructorList;
	private List<Course> courseList;
	
	public ValidateReturnObject(String message, List<Instructor> instructorList, List<Course> courseList) {
		super();
		this.message = message;
		this.instructorList = instructorList;
		this.courseList = courseList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
