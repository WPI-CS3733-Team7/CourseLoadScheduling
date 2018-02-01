package org.dselent.scheduling.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.extractor.CourseSectionsExtractor;
import org.dselent.scheduling.server.extractor.UsersExtractor;
import org.dselent.scheduling.server.miscellaneous.QueryPathConstants;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CustomDaoImpl implements CustomDao
{
	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// can make custom models and extractors as needed or reuse existing ones if applicable
	
	@Override
	public List<User> getAllUsersWithRole(int roleId)
	{
		UsersExtractor extractor = new UsersExtractor();
		String queryTemplate = new String(QueryPathConstants.USERS_WITH_ROLE_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("roleId", roleId);
	    List<User> usersWithRoleList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return usersWithRoleList;
	}

	@Override
	public List<CourseSection> getSectionsByInstructor(int instructorId, int year, String term) {
		CourseSectionsExtractor extractor = new CourseSectionsExtractor();
		String queryTemplate = new String(QueryPathConstants.SECTIONS_BY_INSTRUCTOR_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("instructorId", instructorId);
	    parameters.addValue("year", year);
	    parameters.addValue("term", term);
	    List<CourseSection> sectionsByInstructorList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return sectionsByInstructorList;
	}

	@Override
	public List<CourseSection> getSectionsByCourse(int courseId, int year, String term) {
		CourseSectionsExtractor extractor = new CourseSectionsExtractor();
		String queryTemplate = new String(QueryPathConstants.SECTIONS_BY_COURSE_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("courseId", courseId);
	    parameters.addValue("year", year);
	    parameters.addValue("term", term);
	    List<CourseSection> sectionsByCourseList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return sectionsByCourseList;
	}

	@Override
	public Map<Instructor, Integer> getAllInstructorsWithNumSections(int year, String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Course, Integer> getAllCoursesWithNumSections(int year, String term) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
