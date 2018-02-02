package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorUserLink;
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
public class InstructorUserLinksDaoTest
{
	@Autowired
	private InstructorUserLinksDao instructorUserLinksDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testInstructorUserLinksDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	InstructorUserLink link1 = new InstructorUserLink();
    	link1.setInstructorId(1);
    	link1.setLinkedUserId(17);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID));
    	insertColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID));
    	
    	keyHolderColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.ID));
    	keyHolderColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.UPDATED_AT));
   	
    	instructorUserLinksDao.insert(link1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID);
    	Integer oldInstructorId = 1;
    	Integer newInstructorId = 2;
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
    	
    	String selectColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID);
    	int selectInstructorName = newInstructorId;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectInstructorIdTerm = new QueryTerm();
    	selectInstructorIdTerm.setColumnName(selectColumnName);
    	selectInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectInstructorIdTerm.setValue(selectInstructorName);
    	selectQueryTermList.add(selectInstructorIdTerm);
    	
    	List<String> selectColumnNameList = InstructorUserLink.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<InstructorUserLink> selectedInstructorList = instructorUserLinksDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
		System.out.println(selectedInstructorList);
		scan.next();
		
		// DELETE
		
		String deleteColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID);
		int deleteInstructorId = newInstructorId;
		
		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
		QueryTerm deleteInstructorIdTerm = new QueryTerm();
		deleteInstructorIdTerm.setColumnName(deleteColumnName);
		deleteInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deleteInstructorIdTerm.setValue(deleteInstructorId);
		deleteQueryTermList.add(deleteInstructorIdTerm);
		
		instructorUserLinksDao.delete(deleteQueryTermList);
		
		scan.next();
		scan.close();
		
    	System.out.println();
    }
}
