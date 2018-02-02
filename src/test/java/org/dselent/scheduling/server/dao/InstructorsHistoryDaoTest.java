package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.InstructorsHistoryDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorHistory;
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
public class InstructorsHistoryDaoTest
{
	@Autowired
	private InstructorsHistoryDao instructorsDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testInstructorHistorysDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	InstructorHistory instructor1 = new InstructorHistory();
    	instructor1.setFirstName("instructor");
    	instructor1.setLastName("one");
    	instructor1.setEmail("instructorone@wpi.edu");
    	instructor1.setRank("test proctor");; // simplified for now
    	instructor1.setFormerId(1);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME));
    	insertColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.LAST_NAME));
    	insertColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.EMAIL));
    	insertColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.RANK));
    	insertColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.FORMER_ID));
    	
    	keyHolderColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.ID));
    	keyHolderColumnNameList.add(InstructorHistory.getColumnName(InstructorHistory.Columns.CREATED_AT));
   	
    	instructorsDao.insert(instructor1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME);
    	String oldInstructorHistoryName = "instructor";
    	String newInstructorHistoryName = "newInstructorHistoryName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateInstNameTerm = new QueryTerm();
    	updateInstNameTerm.setColumnName(updateColumnName);
    	updateInstNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateInstNameTerm.setValue(oldInstructorHistoryName);
    	updateQueryTermList.add(updateInstNameTerm);
    	
    	instructorsDao.update(updateColumnName, newInstructorHistoryName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by instructor name
    	
    	String selectColumnName = InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME);
    	String selectInstructorHistoryHistoryName = newInstructorHistoryName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectInstNameTerm = new QueryTerm();
    	selectInstNameTerm.setColumnName(selectColumnName);
    	selectInstNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectInstNameTerm.setValue(selectInstructorHistoryHistoryName);
    	selectQueryTermList.add(selectInstNameTerm);
    	
    	List<String> selectColumnNameList = InstructorHistory.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<InstructorHistory> selectedInstructorHistoryList = instructorsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
		System.out.println(selectedInstructorHistoryList);
		scan.next();
		
		// DELETE
		
		String deleteColumnName = InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME);
		String deleteInstName = newInstructorHistoryName;
		
		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
		QueryTerm deleteInstNameTerm = new QueryTerm();
		deleteInstNameTerm.setColumnName(deleteColumnName);
		deleteInstNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deleteInstNameTerm.setValue(deleteInstName);
		deleteQueryTermList.add(deleteInstNameTerm);
		
		instructorsDao.delete(deleteQueryTermList);
		
		scan.next();
		scan.close();
		
    	System.out.println();
    }
}
