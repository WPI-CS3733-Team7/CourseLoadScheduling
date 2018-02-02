package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.ReplyTypesDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.ReplyType;
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
public class ReplyTypesDaoTest
{
	@Autowired
	private ReplyTypesDao replyTypesDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testReplyTypesDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	ReplyType replyType1 = new ReplyType();
    	replyType1.setReplyType("TEST");
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE));
    	
    	keyHolderColumnNameList.add(ReplyType.getColumnName(ReplyType.Columns.ID));
   	
    	replyTypesDao.insert(replyType1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE);
    	String oldReplyType = "TEST";
    	String newReplyType = "test";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldReplyType);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	replyTypesDao.update(updateColumnName, newReplyType, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE);
    	String selectReplyType = newReplyType;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectReplyType);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = ReplyType.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<ReplyType> selectedReplyTypeList = replyTypesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
		System.out.println(selectedReplyTypeList);
		scan.next();
		
		// DELETE
	
		String deleteColumnName = ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE);
		String deleteReplyType = newReplyType;
		
		List<QueryTerm> deleteQueryTermList = new ArrayList<>();
		QueryTerm deleteUseNameTerm = new QueryTerm();
		deleteUseNameTerm.setColumnName(deleteColumnName);
		deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		deleteUseNameTerm.setValue(deleteReplyType);
		deleteQueryTermList.add(deleteUseNameTerm);
		
		replyTypesDao.delete(deleteQueryTermList);
		
		scan.next();
		scan.close();
			
    }
}
