package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSection;
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
public class CourseSectionsDaoTest
{
	@Autowired
	private CourseSectionsDao courseSectionsDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCourseSectionsDao() throws SQLException
    {
    	Scanner scan = new Scanner(System.in);
    	// INSERT
    	
    	CourseSection crsec1 = new CourseSection();
    	crsec1.setSectionName("TestCourseSection101");
    	crsec1.setSectionId(1);
    	crsec1.setSectionType("Type1");
    	crsec1.setPopulation(100);
    	crsec1.setCourseId(1);
    	crsec1.setInstructorId(1);
    	crsec1.setCalendarInfoId(65);
    	crsec1.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_ID));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.POPULATION));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.CALENDAR_INFO_ID));
    	insertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.ID));
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT));
   	
    	courseSectionsDao.insert(crsec1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	
    	// UPDATE
    	
    	String updateColumnName = CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME);
    	String oldCourseSectionName = "TestCourseSection101";
    	String newCourseSectionName = "newCourseSectionName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateCourseSectionTerm = new QueryTerm();
    	updateCourseSectionTerm.setColumnName(updateColumnName);
    	updateCourseSectionTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateCourseSectionTerm.setValue(oldCourseSectionName);
    	updateQueryTermList.add(updateCourseSectionTerm);
    	
    	courseSectionsDao.update(updateColumnName, newCourseSectionName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME);
    	String selectUserName = newCourseSectionName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCourseSectionTerm = new QueryTerm();
    	selectCourseSectionTerm.setColumnName(selectColumnName);
    	selectCourseSectionTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCourseSectionTerm.setValue(selectUserName);
    	selectQueryTermList.add(selectCourseSectionTerm);
    	
    	List<String> selectColumnNameList = CourseSection.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<CourseSection> selectedCourseSectionList = courseSectionsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
    	System.out.println(selectedCourseSectionList);
    	
    	scan.next();
    	
    	// DELETED
    	String deleteColumnName = CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME);
    		String deleteCourseSectionName = newCourseSectionName;
    		
    		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
    		QueryTerm deleteCourseSectionTerm = new QueryTerm();
    		deleteCourseSectionTerm.setColumnName(deleteColumnName);
    		deleteCourseSectionTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		deleteCourseSectionTerm.setValue(deleteCourseSectionName);
    		deleteQueryTermList.add(deleteCourseSectionTerm);
    		
    		courseSectionsDao.delete(deleteQueryTermList);

    	scan.next();
    	scan.close();

    }
}

