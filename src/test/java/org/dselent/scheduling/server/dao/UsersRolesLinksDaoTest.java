package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.UsersRolesLink;
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

public class UsersRolesLinksDaoTest {
	
	@Autowired
	private UsersRolesLinksDao usersRolesLinksDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    public void testUserRolesLinksDao() throws SQLException
    {
    	
    	Scanner scan = new Scanner(System.in);
    	
    	// INSERT
    	
    	UsersRolesLink usersRolesLink = new UsersRolesLink();
    	usersRolesLink.setRoleId(1);
    	usersRolesLink.setUserId(17);
    	usersRolesLink.setDeleted(false);
    	
    	List<String> insertColumnNameList = new ArrayList<>();
    	List<String> keyHolderColumnNameList = new ArrayList<>();
    	
    	insertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID));
    	insertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.USER_ID));
    	insertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.DELETED));
    	
    	keyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ID));
    	keyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.CREATED_AT));
   	
    	usersRolesLinksDao.insert(usersRolesLink, insertColumnNameList, keyHolderColumnNameList);
    	
    	scan.next();
    	
    	// UPDATE
    	
    	String updateColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID);
    	Integer oldRoleId = 1;
    	Integer newRoleId = 2;
    	List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(oldRoleId);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	usersRolesLinksDao.update(updateColumnName, newRoleId, updateQueryTermList);
    	
    	scan.next();
    	
    	// SELECT
    	// by user name
    	
    	String selectColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID);
    Integer selectUserName = newRoleId;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectUserName);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = UsersRolesLink.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
		@SuppressWarnings("unused")
		List<UsersRolesLink> selectedUserList = usersRolesLinksDao.select(selectColumnNameList, selectQueryTermList, orderByList);
	
	System.out.println(selectedUserList);
	scan.next();
	
	// DELETE
	
	String deleteColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID);
	Integer deleteRoleName = newRoleId;
	
	List<QueryTerm> deleteQueryTermList = new ArrayList<>();
	QueryTerm deleteUseNameTerm = new QueryTerm();
	deleteUseNameTerm.setColumnName(deleteColumnName);
	deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	deleteUseNameTerm.setValue(deleteRoleName);
	deleteQueryTermList.add(deleteUseNameTerm);
	
	usersRolesLinksDao.delete(deleteQueryTermList);
	
	scan.next();
	scan.close();
    }
}