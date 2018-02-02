package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.UserRole;
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
public class UserRolesDaoTest
{
	@Autowired
	private UserRolesDao userRolesDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testUserRolesDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	UserRole userRole1 = new UserRole();
    	userRole1.setRoleName("DICTATOR TEST ROLE");
    	userRole1.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(UserRole.getColumnName(UserRole.Columns.ROLE_NAME));
    	insertColumnNameList.add(UserRole.getColumnName(UserRole.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(UserRole.getColumnName(UserRole.Columns.ID));
    	keyHolderColumnNameList.add(UserRole.getColumnName(UserRole.Columns.CREATED_AT));
    	keyHolderColumnNameList.add(UserRole.getColumnName(UserRole.Columns.UPDATED_AT));
   	
    	userRolesDao.insert(userRole1, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = UserRole.getColumnName(UserRole.Columns.ROLE_NAME);
    	String oldRoleName = "DICTATOR TEST ROLE";
    	String newRoleName = "PLEBIAN";
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldRoleName);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	userRolesDao.update(updateColumnName, newRoleName, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = UserRole.getColumnName(UserRole.Columns.ROLE_NAME);
    	String selectUserName = newRoleName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectUserName);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = UserRole.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<UserRole> selectedUserList = userRolesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
	System.out.println(selectedUserList);
	scan.next();
	
	// DELETE
	
	String deleteColumnName = UserRole.getColumnName(UserRole.Columns.ROLE_NAME);
	String deleteRoleName = newRoleName;
	
	List<QueryTerm> deleteQueryTermList = new ArrayList<>();
	QueryTerm deleteUseNameTerm = new QueryTerm();
	deleteUseNameTerm.setColumnName(deleteColumnName);
	deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	deleteUseNameTerm.setValue(deleteRoleName);
	deleteQueryTermList.add(deleteUseNameTerm);
	
	userRolesDao.delete(deleteQueryTermList);
	
	scan.next();
	scan.close();
		
    	System.out.println();
    }
}