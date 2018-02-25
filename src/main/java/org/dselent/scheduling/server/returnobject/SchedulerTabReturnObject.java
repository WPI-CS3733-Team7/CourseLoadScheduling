package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.Instructor;

import java.util.List;

public class SchedulerTabReturnObject {
    
	Integer linkedInstructorId;
	List<Instructor> instructorList;
    List<Course> courseList;
    List<CourseLoad> courseLoadList;

    public SchedulerTabReturnObject(Integer linkedInstructorId, List<Instructor> instructorList, List<Course> courseList, List<CourseLoad> courseLoadList) {
        this.linkedInstructorId = linkedInstructorId;
    	this.instructorList = instructorList;
        this.courseList = courseList;
        this.courseLoadList = courseLoadList;
    }

	public Integer getLinkedInstructorId() {
		return linkedInstructorId;
	}

	public void setLinkedInstructorId(Integer linkedInstructorId) {
		this.linkedInstructorId = linkedInstructorId;
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
