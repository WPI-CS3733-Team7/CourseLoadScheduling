package org.dselent.scheduling.server.returnobject;

import java.util.List;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;

public class ValidateReturnObject {

	private Boolean success;
	private List<Instructor> instructorList;
	private List<Course> courseList;
	
	public ValidateReturnObject(Boolean success, List<Instructor> instructorList, List<Course> courseList) {
		super();
		this.success = success;
		this.instructorList = instructorList;
		this.courseList = courseList;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
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
