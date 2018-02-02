package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CourseSectionsHistoryDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSectionHistory;
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
public class CourseSectionHistoryDaoTest
{
	@Autowired
	private CourseSectionsHistoryDao courseSectionHistoryDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCourseSectionHistoryDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	CourseSectionHistory crsech1 = new CourseSectionHistory();
    	/*
    	 * ID,
			FORMER_ID,
			SECTION_NAME,
			SECTION_ID,
			SECTION_TYPE,
			POPULATION,
			COURSE_ID,
			INSTRUCTOR_ID,
			CALENDAR_INFO_ID,
			CREATED_AT
    	 */
    	crsech1.setFormerId(1010);
    	crsech1.setSectionName("TestCourseSection101");
    	crsech1.setSectionId(10199);
    	crsech1.setSectionType("Type1");
    	crsech1.setPopulation(100);
    	crsech1.setCourseId(1010);
    	crsech1.setInstructorId(2009);
    	crsech1.setCalendarInfoId(209);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.FORMER_ID));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_ID));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_TYPE));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.POPULATION));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.COURSE_ID));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.INSTRUCTOR_ID));
    	insertColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CALENDAR_INFO_ID));
    	
    	keyHolderColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.ID));
    	keyHolderColumnNameList.add(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CREATED_AT));
  
   	
    	courseSectionHistoryDao.insert(crsech1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME);
    	String oldCrsechName = "TestCourseSection101";
    	String newCrsechName = "newCourseSectionName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateCrsecHTerm = new QueryTerm();
    	updateCrsecHTerm.setColumnName(updateColumnName);
    	updateCrsecHTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateCrsecHTerm.setValue(oldCrsechName);
    	updateQueryTermList.add(updateCrsecHTerm);
    	
    	courseSectionHistoryDao.update(updateColumnName, newCrsechName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME);
    	String selectCourseSectionName = newCrsechName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCrsecHTerm = new QueryTerm();
    	selectCrsecHTerm.setColumnName(selectColumnName);
    	selectCrsecHTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCrsecHTerm.setValue(selectCourseSectionName);
    	selectQueryTermList.add(selectCrsecHTerm);
    	
    	List<String> selectColumnNameList = CourseSectionHistory.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<CourseSectionHistory> selectedCourseSectionList = courseSectionHistoryDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
	System.out.println(selectedCourseSectionList);
	scan.next();
	
	// DELETE
	
	scan.close();
		
    	System.out.println();
    }
}
