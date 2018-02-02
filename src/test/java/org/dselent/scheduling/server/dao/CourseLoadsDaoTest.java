package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.dao.CourseLoadsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseLoad;
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

public class CourseLoadsDaoTest {
   
	@Autowired
    private CourseLoadsDao courseLoadsDao;

    /*
     * Not really an using this as a JUnit test
     * More of an example on how to use the classes
     */
    @Test
    public void testCourseLoadsDao() throws SQLException
    {
        Scanner scan = new Scanner(System.in);
        // INSERT

        CourseLoad courseLoad1 = new CourseLoad();
        courseLoad1.setLoadType("TestCourseLoad");
        courseLoad1.setLoadDescription("TestDescription");
        courseLoad1.setInstructorId(1);
        courseLoad1.setDeleted(false);

        List<String> insertColumnNameList = new ArrayList<>();
        List<String> keyHolderColumnNameList = new ArrayList<>();

        insertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE));
        insertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_DESCRIPTION));
        insertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.INSTRUCTOR_ID));
        insertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.DELETED));

        keyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.ID));
        keyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.CREATED_AT));
        keyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.UPDATED_AT));

        courseLoadsDao.insert(courseLoad1, insertColumnNameList, keyHolderColumnNameList);

        scan.next();

        // UPDATE

        String updateColumnName = CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE);
        String oldLoadType = "oldLoadType1";
        String newLoadType = "newLoadType";
        List<QueryTerm> updateQueryTermList = new ArrayList<>();

        QueryTerm updateUseNameTerm = new QueryTerm();
        updateUseNameTerm.setColumnName(updateColumnName);
        updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        updateUseNameTerm.setValue(oldLoadType);
        updateQueryTermList.add(updateUseNameTerm);

        courseLoadsDao.update(updateColumnName, newLoadType, updateQueryTermList);

        // SELECT
        // by CourseLoad type

        String selectColumnName = CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE);
        String selectLoadType = newLoadType;

        List<QueryTerm> selectQueryTermList = new ArrayList<>();

        QueryTerm selectUseNameTerm = new QueryTerm();
        selectUseNameTerm.setColumnName(selectColumnName);
        selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        selectUseNameTerm.setValue(selectLoadType);
        selectQueryTermList.add(selectUseNameTerm);

        List<String> selectColumnNameList = CourseLoad.getColumnNameList();

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
        orderByList.add(orderPair1);

        @SuppressWarnings("unused")
        List<CourseLoad> selectedUserList = courseLoadsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
        scan.next();

        // DELETE

        String deleteColumnName = CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE);
        String deleteLoadType = newLoadType;

        List<QueryTerm> deleteQueryTermList = new ArrayList<>();
        QueryTerm deleteUseNameTerm = new QueryTerm();
        deleteUseNameTerm.setColumnName(deleteColumnName);
        deleteUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        deleteUseNameTerm.setValue(deleteLoadType);
        deleteQueryTermList.add(deleteUseNameTerm);

        courseLoadsDao.delete(deleteQueryTermList);

        scan.next();
        scan.close();

        System.out.println();
    }
}
