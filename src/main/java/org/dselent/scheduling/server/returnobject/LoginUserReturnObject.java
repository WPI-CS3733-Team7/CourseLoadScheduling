package org.dselent.scheduling.server.returnobject;

import java.util.List;
import java.util.ArrayList;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.Course;

public class LoginUserReturnObject {
	
	Boolean success;
	Integer userId;
	List<Instructor> instructorList;
	List<Course> courseList;
	
	public LoginUserReturnObject(Boolean success, Integer userId, List<Instructor> instructorList, List<Course> courseList) {
		this.success = success;
		this.userId = userId;
		this.instructorList = instructorList;
		this.courseList = courseList;
	}
}
