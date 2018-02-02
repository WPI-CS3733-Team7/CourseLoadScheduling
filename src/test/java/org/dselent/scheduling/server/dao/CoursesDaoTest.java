package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CoursesDaoTest
{
	@Autowired
	private CoursesDao coursesDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCoursesDao() throws SQLException
    {
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	Course course1 = new Course();
    	course1.setCourseName("testCourse1");
    	course1.setCourseNumber("1010");
    	course1.setFrequency("10");
    course1.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(Course.getColumnName(Course.Columns.COURSE_NAME));
    	insertColumnNameList.add(Course.getColumnName(Course.Columns.COURSE_NUMBER));
    	insertColumnNameList.add(Course.getColumnName(Course.Columns.FREQUENCY));
    	insertColumnNameList.add(Course.getColumnName(Course.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(Course.getColumnName(Course.Columns.ID));
    	keyHolderColumnNameList.add(Course.getColumnName(Course.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(Course.getColumnName(Course.Columns.UPDATED_AT));
   	
    	coursesDao.insert(course1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = Course.getColumnName(Course.Columns.COURSE_NAME);
    	String oldcourseName = "testCourse1";
    	String newcourseName = "newcourseName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateCourseNameTerm = new QueryTerm();
    	updateCourseNameTerm.setColumnName(updateColumnName);
    	updateCourseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateCourseNameTerm.setValue(oldcourseName);
    	updateQueryTermList.add(updateCourseNameTerm);
    	
    	coursesDao.update(updateColumnName, newcourseName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = Course.getColumnName(Course.Columns.COURSE_NAME);
    	String selectCourseName = newcourseName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCourseNameTerm = new QueryTerm();
    	selectCourseNameTerm.setColumnName(selectColumnName);
    	selectCourseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCourseNameTerm.setValue(selectCourseName);
    	selectQueryTermList.add(selectCourseNameTerm);
    	
    	List<String> selectColumnNameList = Course.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<Course> selectedCourseList = coursesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
    	System.out.println(selectedCourseList);
    	
    	scan.next();
    	
    	// DELETED
    	String deleteColumnName = Course.getColumnName(Course.Columns.COURSE_NAME);
    		String deleteCourseName = newcourseName;
    		
    		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
    		QueryTerm deleteCourseTerm = new QueryTerm();
    		deleteCourseTerm.setColumnName(deleteColumnName);
    		deleteCourseTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		deleteCourseTerm.setValue(deleteCourseName);
    		deleteQueryTermList.add(deleteCourseTerm);
    		
    		coursesDao.delete(deleteQueryTermList);

    	scan.next();
    	scan.close();

    }
}
