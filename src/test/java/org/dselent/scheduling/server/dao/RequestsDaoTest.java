package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.RequestsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Request;
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
public class RequestsDaoTest
{
	@Autowired
	private RequestsDao requestsDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testRequestsDao() throws SQLException
    {
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	Request request1 = new Request();
    	request1.setRequesterId(1);
    	request1.setRequestType("Course");
    	request1.setRequestDetails("Request Test");
    	request1.setReply("Blah Blah Blah");
    	request1.setReplyType("Tentative");
    	request1.setDeleted(false); 
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.REQUESTER_ID));
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.REQUEST_TYPE));
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.REQUEST_DETAILS));
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.REPLY));
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.REPLY_TYPE));
    	insertColumnNameList.add(Request.getColumnName(Request.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(Request.getColumnName(Request.Columns.ID));
    	keyHolderColumnNameList.add(Request.getColumnName(Request.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(Request.getColumnName(Request.Columns.UPDATED_AT));
   	
    	requestsDao.insert(request1, insertColumnNameList, keyHolderColumnNameList);	
    	
    	// UPDATE
    	
    	String updateColumnName = Request.getColumnName(Request.Columns.REQUESTER_ID);
    	Integer oldRequesterId = 1;
    	Integer newRequesterId = 2;
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldRequesterId);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	requestsDao.update(updateColumnName, newRequesterId, updateQueryTermList);
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = Request.getColumnName(Request.Columns.REQUESTER_ID);
    	Integer selectRequesterId = newRequesterId;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectRequesterId);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = Request.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<Request> selectedRequestList = requestsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
    	System.out.println(selectedRequestList);
    	scan.next();
    	
    	// DELETE
    	
     	String deleteColumnName = Request.getColumnName(Request.Columns.REQUESTER_ID);
     	Integer deleteRequesterId = newRequesterId;
     	
     	List<QueryTerm> deleteQueryTermList = new ArrayList<>();
     	QueryTerm deleteUseNameTerm = new QueryTerm();
     	deleteUseNameTerm.setColumnName(deleteColumnName);
     	deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
     	deleteUseNameTerm.setValue(deleteRequesterId);
     	deleteQueryTermList.add(deleteUseNameTerm);
     	
     	requestsDao.delete(deleteQueryTermList);
     	
     	scan.next();
     	scan.close();
    }
}
