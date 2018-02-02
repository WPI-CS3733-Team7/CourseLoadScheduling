package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
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
public class CalendarInfoDaoTest
{
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCalendarInfoDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	CalendarInfo calendarInfo1 = new CalendarInfo();
    	calendarInfo1.setCalYear(9999);
    	calendarInfo1.setCalTerm("TestTerm");
    	calendarInfo1.setDays("TestDays");
    	calendarInfo1.setStartTime(0000);
    	calendarInfo1.setEndTime(9999); // simplified for now
    	calendarInfo1.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR));
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM));
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS));
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME));
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME));
    	insertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.ID));
    	keyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.UPDATED_AT));
   	
    	calendarInfoDao.insert(calendarInfo1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM);
    	String oldCalTerm = "TestTerm";
    	String newCalTerm = "NewTestTerm";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateCalTermTerm = new QueryTerm();
    	updateCalTermTerm.setColumnName(updateColumnName);
    	updateCalTermTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateCalTermTerm.setValue(oldCalTerm);
    	updateQueryTermList.add(updateCalTermTerm);
    	
    	calendarInfoDao.update(updateColumnName, newCalTerm, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by cal_term
    	
    	String selectColumnName = CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM);
    	String selectCalTerm = newCalTerm;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCalTermTerm = new QueryTerm();
    	selectCalTermTerm.setColumnName(selectColumnName);
    	selectCalTermTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCalTermTerm.setValue(selectCalTerm);
    	selectQueryTermList.add(selectCalTermTerm);
    	
    	List<String> selectColumnNameList = CalendarInfo.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<CalendarInfo> selectedCalendarInfoList = calendarInfoDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
		System.out.println(selectedCalendarInfoList);
		scan.next();
		
		// DELETE
		
		String deleteColumnName = CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM);
		String deleteCalTerm = newCalTerm;
		
		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
		QueryTerm deleteCalTermTerm = new QueryTerm();
		deleteCalTermTerm.setColumnName(deleteColumnName);
		deleteCalTermTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deleteCalTermTerm.setValue(deleteCalTerm);
		deleteQueryTermList.add(deleteCalTermTerm);
		
		calendarInfoDao.delete(deleteQueryTermList);
		
		scan.next();
		scan.close();
			
	    	System.out.println();
    }
}