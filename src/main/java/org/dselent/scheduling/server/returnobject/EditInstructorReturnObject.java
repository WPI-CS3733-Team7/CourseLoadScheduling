package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.Instructor;

public class EditInstructorReturnObject
{
	private Instructor instructor;
	private CourseLoad courseLoad;
	
	public EditInstructorReturnObject(Instructor instructor, CourseLoad courseLoad)
	{
		this.instructor = instructor;
		this.courseLoad = courseLoad;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public CourseLoad getCourseLoad() {
		return courseLoad;
	}
}
