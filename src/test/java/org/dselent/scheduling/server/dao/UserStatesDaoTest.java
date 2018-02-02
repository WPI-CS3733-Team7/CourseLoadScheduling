package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.UserStatesDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.UserState;
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
public class UserStatesDaoTest
{
	@Autowired
	private UserStatesDao userStatesDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testUserRolesDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	UserState userState1 = new UserState();
    	userState1.setState("ACADEMIC LEAVE");
    	userState1.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(UserState.getColumnName(UserState.Columns.STATE));
    	insertColumnNameList.add(UserState.getColumnName(UserState.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.ID));
    	keyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(UserState.getColumnName(UserState.Columns.UPDATED_AT));
   	
    	userStatesDao.insert(userState1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = UserState.getColumnName(UserState.Columns.STATE);
    	String oldStateName = "ACADEMIC LEAVE";
    	String newStateName = "MIND CONTROLLED";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldStateName);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	userStatesDao.update(updateColumnName, newStateName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = UserState.getColumnName(UserState.Columns.STATE);
    	String selectUserName = newStateName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectUserName);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = UserState.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<UserState> selectedUserList = userStatesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
	System.out.println(selectedUserList);
	scan.next();
	
	// DELETE
	
	String deleteColumnName = UserState.getColumnName(UserState.Columns.STATE);
	String deleteRoleName = newStateName;
	
	List<QueryTerm> deleteQueryTermList = new ArrayList<>();
	QueryTerm deleteUseNameTerm = new QueryTerm();
	deleteUseNameTerm.setColumnName(deleteColumnName);
	deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	deleteUseNameTerm.setValue(deleteRoleName);
	deleteQueryTermList.add(deleteUseNameTerm);
	
	userStatesDao.delete(deleteQueryTermList);
	
	scan.next();
	scan.close();
    }
}