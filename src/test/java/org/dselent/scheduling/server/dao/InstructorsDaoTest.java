package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Instructor;
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
public class InstructorsDaoTest
{
	@Autowired
	private InstructorsDao instructorsDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testInstructorsDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	Instructor instructor1 = new Instructor();
    	instructor1.setFirstName("instructor");
    	instructor1.setLastName("one");
    	instructor1.setEmail("instructorone@wpi.edu");
    	instructor1.setRank("test proctor");; // simplified for now
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.FIRST_NAME));
    	insertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.LAST_NAME));
    	insertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.EMAIL));
    	insertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.RANK));
    	
    	keyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.ID));
    	keyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.UPDATED_AT));
   	
    	instructorsDao.insert(instructor1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
    	String oldInstructorName = "instructor";
    	String newInstructorName = "newInstructorName";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateInstNameTerm = new QueryTerm();
    	updateInstNameTerm.setColumnName(updateColumnName);
    	updateInstNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateInstNameTerm.setValue(oldInstructorName);
    	updateQueryTermList.add(updateInstNameTerm);
    	
    	instructorsDao.update(updateColumnName, newInstructorName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by instructor name
    	
    	String selectColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
    	String selectInstructorName = newInstructorName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectInstNameTerm = new QueryTerm();
    	selectInstNameTerm.setColumnName(selectColumnName);
    	selectInstNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectInstNameTerm.setValue(selectInstructorName);
    	selectQueryTermList.add(selectInstNameTerm);
    	
    	List<String> selectColumnNameList = Instructor.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<Instructor> selectedInstructorList = instructorsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
		System.out.println(selectedInstructorList);
		scan.next();
		
		// DELETE
		
		String deleteColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
		String deleteInstName = newInstructorName;
		
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
