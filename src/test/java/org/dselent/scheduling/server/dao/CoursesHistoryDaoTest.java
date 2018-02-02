package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CoursesHistoryDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseHistory;
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
public class CoursesHistoryDaoTest
{
	@Autowired
	private CoursesHistoryDao coursesHistoryDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCoursesHistoryDao() throws SQLException
    {
    	// INSERT
    	
    CourseHistory crshis1 = new CourseHistory();
    crshis1.setFormerId(1010);
    crshis1.setCourseName("TestCourse101");
    crshis1.setCourseNumber("1010");
    crshis1.setFrequency("10");
    
    
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.FORMER_ID));
    	insertColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NAME));
    	insertColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NUMBER));
    	insertColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.FREQUENCY));
    	
    	keyHolderColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.ID));
    	keyHolderColumnNameList.add(CourseHistory.getColumnName(CourseHistory.Columns.CREATED_AT));

   	
    	coursesHistoryDao.insert(crshis1, insertColumnNameList, keyHolderColumnNameList);
    	
    	
    	// UPDATE
    	
    	String updateColumnName = CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NAME);
    	String oldcourseName = "TestCourse101";
    	String newcourseName = "newCourseName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateCourseHistoryTerm = new QueryTerm();
    	updateCourseHistoryTerm.setColumnName(updateColumnName);
    	updateCourseHistoryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateCourseHistoryTerm.setValue(oldcourseName);
    	updateQueryTermList.add(updateCourseHistoryTerm);
    	
    	coursesHistoryDao.update(updateColumnName, newcourseName, updateQueryTermList);
    	
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NAME);
    	String selectCourseName = newcourseName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCourseHistoryTerm = new QueryTerm();
    	selectCourseHistoryTerm.setColumnName(selectColumnName);
    	selectCourseHistoryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCourseHistoryTerm.setValue(selectCourseName);
    	selectQueryTermList.add(selectCourseHistoryTerm);
    	
    	List<String> selectColumnNameList = CourseHistory.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<CourseHistory> selectedCourseHistoryList = coursesHistoryDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
    	System.out.println();
    }
}
