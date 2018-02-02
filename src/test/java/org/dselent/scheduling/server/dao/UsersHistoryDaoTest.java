package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.UsersHistoryDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.UsersHistory;
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

public class UsersHistoryDaoTest {
    @Autowired
    private UsersHistoryDao usersHistoryDao;

    /*
     * Not really an using this as a JUnit test
     * More of an example on how to use the classes
     */
    @Test
    public void testUsersDao() throws SQLException
    {
        Scanner scan = new Scanner(System.in);
        // INSERT

        UsersHistory userHistory1 = new UsersHistory();
        userHistory1.setUserId(9);
        userHistory1.setUserName("TestUserName");
        userHistory1.setFirstName("TestFirstName");
        userHistory1.setLastName("TestLastName");
        userHistory1.setEmail("TestEmail");
        userHistory1.setSalt("TestSalt");
        userHistory1.setUserStateId(10);

        List<String> insertColumnNameList = new ArrayList<>();
        List<String> keyHolderColumnNameList = new ArrayList<>();

        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.USER_ID));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.USER_NAME));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.FIRST_NAME));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.LAST_NAME));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.EMAIL));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.SALT));
        insertColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.USER_STATE_ID));

        keyHolderColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.ID));
        keyHolderColumnNameList.add(UsersHistory.getColumnName(UsersHistory.Columns.CREATED_AT));

        usersHistoryDao.insert(userHistory1, insertColumnNameList, keyHolderColumnNameList);

        scan.next();
        // UPDATE

        String updateColumnName = UsersHistory.getColumnName(UsersHistory.Columns.USER_NAME);
        String oldUserName = "oldUserName1";
        String newUserName = "newUserName";
        List<QueryTerm> updateQueryTermList = new ArrayList<>();

        QueryTerm updateUseNameTerm = new QueryTerm();
        updateUseNameTerm.setColumnName(updateColumnName);
        updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        updateUseNameTerm.setValue(oldUserName);
        updateQueryTermList.add(updateUseNameTerm);

        usersHistoryDao.update(updateColumnName, newUserName, updateQueryTermList);
        scan.next();

        // SELECT
        // by UsersHistory name

        String selectColumnName = UsersHistory.getColumnName(UsersHistory.Columns.USER_NAME);
        String selectLoadType = newUserName;

        List<QueryTerm> selectQueryTermList = new ArrayList<>();

        QueryTerm selectUseNameTerm = new QueryTerm();
        selectUseNameTerm.setColumnName(selectColumnName);
        selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        selectUseNameTerm.setValue(selectLoadType);
        selectQueryTermList.add(selectUseNameTerm);

        List<String> selectColumnNameList = UsersHistory.getColumnNameList();

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
        orderByList.add(orderPair1);

        @SuppressWarnings("unused")
        List<UsersHistory> selectedUserList = usersHistoryDao.select(selectColumnNameList, selectQueryTermList, orderByList);

        System.out.println();

        scan.next();
        scan.close();
    }
}
