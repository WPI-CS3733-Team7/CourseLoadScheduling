package org.dselent.scheduling.server.dao.impl;

import java.util.List;

import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.extractor.CourseSectionsExtractor;
import org.dselent.scheduling.server.extractor.IntegerPairExtractor;
import org.dselent.scheduling.server.extractor.UsersExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryPathConstants;
import org.dselent.scheduling.server.model.CourseSection;
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

	//Returns a list of all course sections with specified instructor, year, and term
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

	//Returns a list of all course sections with specified course, year, and term
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

	//Returns the id of each instructor paired with a count of associated course sections in the specified year and term
	@Override
	public List<Pair<Integer, Integer>> getAllInstructorsWithNumSections(int year, String term) {
		IntegerPairExtractor extractor = new IntegerPairExtractor();
		String queryTemplate = new String(QueryPathConstants.INSTRUCTORS_WITH_NUM_SECTIONS_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("year", year);
	    parameters.addValue("term", term);
	    List<Pair<Integer, Integer>> instructorSectionPairsList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return instructorSectionPairsList;
	}

	//Returns the id of each course paired with a count of associated course sections in the specified year and term
	@Override
	public List<Pair<Integer, Integer>> getAllCoursesWithNumSections(int year, String term) {
		IntegerPairExtractor extractor = new IntegerPairExtractor();
		String queryTemplate = new String(QueryPathConstants.COURSES_WITH_NUM_SECTIONS_QUERY);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("year", year);
	    parameters.addValue("term", term);
	    List<Pair<Integer, Integer>> courseSectionPairsList = namedParameterJdbcTemplate.query(queryTemplate, parameters, extractor);
	    return courseSectionPairsList;
	}
	
}
