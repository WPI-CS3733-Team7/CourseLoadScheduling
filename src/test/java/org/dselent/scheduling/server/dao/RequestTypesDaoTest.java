package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.RequestTypesDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Request;
import org.dselent.scheduling.server.model.RequestType;
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
public class RequestTypesDaoTest
{
	@Autowired
	private RequestTypesDao requestTypesDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testUsersDao() throws SQLException
    {
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	RequestType requestType1 = new RequestType();
    	requestType1.setRequestType("TEST");
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(RequestType.getColumnName(RequestType.Columns.REQUEST_TYPE));

    	keyHolderColumnNameList.add(RequestType.getColumnName(RequestType.Columns.ID));
   	
    	requestTypesDao.insert(requestType1, insertColumnNameList, keyHolderColumnNameList);
    	
    	// UPDATE
    	
    	String updateColumnName = RequestType.getColumnName(RequestType.Columns.REQUEST_TYPE);
    	String oldRequestType = "TEST";
    	String newRequestType = "test";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldRequestType);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	requestTypesDao.update(updateColumnName, newRequestType, updateQueryTermList);
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = RequestType.getColumnName(RequestType.Columns.REQUEST_TYPE);
    	String selectRequestType = newRequestType;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectRequestType);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = RequestType.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<RequestType> selectedRequestTypeList = requestTypesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    	
    	System.out.println();
    	scan.next();
    	
    	// DELETE
    	
     	String deleteColumnName = RequestType.getColumnName(RequestType.Columns.REQUEST_TYPE);
     	String deleteRequestType = newRequestType;
     	
     	List<QueryTerm> deleteQueryTermList = new ArrayList<>();
     	QueryTerm deleteUseNameTerm = new QueryTerm();
     	deleteUseNameTerm.setColumnName(deleteColumnName);
     	deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
     	deleteUseNameTerm.setValue(deleteRequestType);
     	deleteQueryTermList.add(deleteUseNameTerm);
     	
     	requestTypesDao.delete(deleteQueryTermList);
     	
     	scan.next();
     	scan.close();
    	
    }
}
