package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CourseLoadsHistoryDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseLoadHistory;
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

public class CourseLoadsHistoryDaoTest {
    @Autowired
    private CourseLoadsHistoryDao courseLoadsHistoryDao;

    /*
     * Not really an using this as a JUnit test
     * More of an example on how to use the classes
     */
    @Test
    public void testUsersDao() throws SQLException
    {
        Scanner scan = new Scanner(System.in);
        // INSERT

        CourseLoadHistory courseLoadHistory1 = new CourseLoadHistory();
        courseLoadHistory1.setFormerId(10);
        courseLoadHistory1.setLoadType("TestCourseLoadHistory");
        courseLoadHistory1.setLoadDescription("TestDescription");
        courseLoadHistory1.setInstructorId(10);

        List<String> insertColumnNameList = new ArrayList<>();
        List<String> keyHolderColumnNameList = new ArrayList<>();

        insertColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.FORMER_ID));
        insertColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_TYPE));
        insertColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_DESCRIPTION));
        insertColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.INSTRUCTOR_ID));

        keyHolderColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.ID));
        keyHolderColumnNameList.add(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.CREATED_AT));

        courseLoadsHistoryDao.insert(courseLoadHistory1, insertColumnNameList, keyHolderColumnNameList);

        scan.next();
        // UPDATE

        String updateColumnName = CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_TYPE);
        String oldLoadType = "TestCourseLoadHistory";
        String newLoadType = "newLoadType";
        List<QueryTerm> updateQueryTermList = new ArrayList<>();

        QueryTerm updateUseNameTerm = new QueryTerm();
        updateUseNameTerm.setColumnName(updateColumnName);
        updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        updateUseNameTerm.setValue(oldLoadType);
        updateQueryTermList.add(updateUseNameTerm);

        courseLoadsHistoryDao.update(updateColumnName, newLoadType, updateQueryTermList);
        scan.next();

        // SELECT
        // by CourseLoadHistory type

        String selectColumnName = CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_TYPE);
        String selectLoadType = newLoadType;

        List<QueryTerm> selectQueryTermList = new ArrayList<>();

        QueryTerm selectUseNameTerm = new QueryTerm();
        selectUseNameTerm.setColumnName(selectColumnName);
        selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        selectUseNameTerm.setValue(selectLoadType);
        selectQueryTermList.add(selectUseNameTerm);

        List<String> selectColumnNameList = CourseLoadHistory.getColumnNameList();

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
        orderByList.add(orderPair1);

        @SuppressWarnings("unused")
        List<CourseLoadHistory> selectedUserList = courseLoadsHistoryDao.select(selectColumnNameList, selectQueryTermList, orderByList);

        System.out.println(selectedUserList);

        scan.next();
        scan.close();
    }
}
