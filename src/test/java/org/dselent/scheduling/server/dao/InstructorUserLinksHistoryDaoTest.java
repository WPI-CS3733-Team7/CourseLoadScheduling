package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorUserLinkHistory;
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
public class InstructorUserLinksHistoryDaoTest
{
	@Autowired
	private InstructorUserLinksHistoryDao instructorUserLinksDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testInstructorUserLinksHistoryDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	InstructorUserLinkHistory link1 = new InstructorUserLinkHistory();
    	link1.setInstructorId(1);
    	link1.setLinkedUserId(17);
    	link1.setFormerId(1);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID));
    	insertColumnNameList.add(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.LINKED_USER_ID));
    	insertColumnNameList.add(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.FORMER_ID));
    	
    	keyHolderColumnNameList.add(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.ID));
    	keyHolderColumnNameList.add(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.CREATED_AT));
   	
    	instructorUserLinksDao.insert(link1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID);
    	int oldInstructorId = 1;
    	int newInstructorId = 2;
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateInstructorIdTerm = new QueryTerm();
    	updateInstructorIdTerm.setColumnName(updateColumnName);
    	updateInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateInstructorIdTerm.setValue(oldInstructorId);
    	updateQueryTermList.add(updateInstructorIdTerm);
    	
    	instructorUserLinksDao.update(updateColumnName, newInstructorId, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by instructor name
    	
    	String selectColumnName = InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID);
    	int selectInstructorName = newInstructorId;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectInstructorIdTerm = new QueryTerm();
    	selectInstructorIdTerm.setColumnName(selectColumnName);
    	selectInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectInstructorIdTerm.setValue(selectInstructorName);
    	selectQueryTermList.add(selectInstructorIdTerm);
    	
    	List<String> selectColumnNameList = InstructorUserLinkHistory.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<InstructorUserLinkHistory> selectedInstructorList = instructorUserLinksDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
		System.out.println(selectedInstructorList);
				
		scan.next();
		scan.close();
		
    	System.out.println();
    }
}
