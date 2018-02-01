package org.dselent.scheduling.server.dao;

import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.Course;
import org.springframework.stereotype.Repository;

/**
 * Interface for all daos for custom queries.
 * 
 * @author dselent
 *
 */
@Repository
public interface CustomDao
{
	// custom queries here
	
	public List<User> getAllUsersWithRole(int roleId);
	public List<CourseSection> getSectionsByInstructor(int instructorId, int year, String term);
	public List<CourseSection> getSectionsByCourse(int courseId, int year, String term);
	public Map<Instructor, Integer> getAllInstructorsWithNumSections(int year, String term);
	public Map<Course, Integer> getAllCoursesWithNumSections(int year, String term);
}
