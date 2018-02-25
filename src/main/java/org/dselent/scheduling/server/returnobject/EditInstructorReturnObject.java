package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.Instructor;

public class EditInstructorReturnObject
{
	Instructor instructor;
	CourseLoad courseLoad;
	
	public EditInstructorReturnObject(Instructor instructor, CourseLoad courseLoad)
	{
		this.instructor = instructor;
		this.courseLoad = courseLoad;
	}
}
