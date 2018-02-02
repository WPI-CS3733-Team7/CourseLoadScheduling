package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfoHistory;
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
public class CalendarInfoHistoryDaoTest
{
	@Autowired
	private CalendarInfoHistoryDao calendarInfoDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testCalendarInfoHistoryDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	CalendarInfoHistory calendarInfo1 = new CalendarInfoHistory();
    	calendarInfo1.setCalYear(9999);
    	calendarInfo1.setCalTerm("TestTerm");
    	calendarInfo1.setDays("TestDays");
    	calendarInfo1.setStartTime(0000);
    	calendarInfo1.setEndTime(9999); // simplified for now
    	calendarInfo1.setFormerId(1);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_YEAR));
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM));
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.DAYS));
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.START_TIME));
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.END_TIME));
    	insertColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.FORMER_ID));
    	
    	keyHolderColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.ID));
    	keyHolderColumnNameList.add(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CREATED_AT));
   	
    	calendarInfoDao.insert(calendarInfo1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM);
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
    	
    	String selectColumnName = CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM);
    	String selectCalTerm = newCalTerm;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectCalTermTerm = new QueryTerm();
    	selectCalTermTerm.setColumnName(selectColumnName);
    	selectCalTermTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectCalTermTerm.setValue(selectCalTerm);
    	selectQueryTermList.add(selectCalTermTerm);
    	
    	List<String> selectColumnNameList = CalendarInfoHistory.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<CalendarInfoHistory> selectedCalendarInfoList = calendarInfoDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
		System.out.println(selectedCalendarInfoList);
		
		scan.next();
		scan.close();
			
	    	System.out.println();
    }
}