package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.returnobject.SelectCourseReturnObject;
import org.springframework.stereotype.Service;

/**
 * Service layer to specify all business logic. Calls the dao layer when data retrieval is needed.
 * Interfaces specify the behavior and the implementation provide the specific implementations.
 * 
 * @author dselent
 *
 */
@Service
public interface CourseService
{
	/**
	 * 
	 */
	 public SelectCourseReturnObject selectCourse(Course C, CalendarInfo ci) throws SQLException;
	 

	 public List<Course> editCourse(Course newCourse) throws SQLException;

	 
}
