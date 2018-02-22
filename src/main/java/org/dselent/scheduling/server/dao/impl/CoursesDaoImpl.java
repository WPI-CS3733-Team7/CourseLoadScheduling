package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.extractor.CoursesExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CoursesDaoImpl extends BaseDaoImpl<Course> implements CoursesDao
{
    @Override
    public int insert(Course courseModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
    {

        validateColumnNames(insertColumnNameList);
        validateColumnNames(keyHolderColumnNameList);

        String queryTemplate = QueryStringBuilder.generateInsertString(Course.TABLE_NAME, insertColumnNameList);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        List<Map<String, Object>> keyList = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        for(String insertColumnName : insertColumnNameList)
        {
            addParameterMapValue(parameters, insertColumnName, courseModel);
        }
        // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
        // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, courseModel));

        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        Map<String, Object> keyMap = keyHolder.getKeys();

        for(String keyHolderColumnName : keyHolderColumnNameList)
        {
            addObjectValue(keyMap, keyHolderColumnName, courseModel);
        }

        return rowsAffected;

    }
    
    @Override
	public Course insertReturnModel(Course model, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException {
    	validateColumnNames(insertColumnNameList);
        validateColumnNames(keyHolderColumnNameList);

        String queryTemplate = QueryStringBuilder.generateInsertString(Course.TABLE_NAME, insertColumnNameList);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        List<Map<String, Object>> keyList = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        for(String insertColumnName : insertColumnNameList)
        {
            addParameterMapValue(parameters, insertColumnName, model);
        }
        // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
        // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, courseModel));

        namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        Map<String, Object> keyMap = keyHolder.getKeys();

        for(String keyHolderColumnName : keyHolderColumnNameList)
        {
            addObjectValue(keyMap, keyHolderColumnName, model);
        }

        return model;
	}

    @Override
    public List<Course> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
    {
        CoursesExtractor extractor = new CoursesExtractor();
        String queryTemplate = QueryStringBuilder.generateSelectString(Course.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        List<Course> courseList = jdbcTemplate.query(queryTemplate, extractor, parameters);

        return courseList;
    }

    @Override
    public Course findById(int id) throws SQLException
    {
        String columnName = QueryStringBuilder.convertColumnName(Course.getColumnName(Course.Columns.ID), false);
        List<String> selectColumnNames = Course.getColumnNameList();

        List<QueryTerm> queryTermList = new ArrayList<>();
        QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
        queryTermList.add(idTerm);

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
        orderByList.add(order);

        List<Course> courseList = select(selectColumnNames, queryTermList, orderByList);

        Course course = null;

        if(!courseList.isEmpty())
        {
            course = courseList.get(0);
        }

        return course;
    }

    @Override
    public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
    {
        String queryTemplate = QueryStringBuilder.generateUpdateString(Course.TABLE_NAME, columnName, queryTermList);

        List<Object> objectList = new ArrayList<Object>();
        objectList.add(newValue);

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);

        return rowsAffected;
    }

    @Override
    public int delete(List<QueryTerm> queryTermList)
    {
        String queryTemplate = QueryStringBuilder.generateDeleteString(Course.TABLE_NAME, queryTermList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);

        return rowsAffected;
    }

    private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, Course courseModel)
    {
        String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);

        // Wish this could generalize
        // The getter must be distinguished unless the models are designed as simply a map of columns-values
        // Would prefer not being that generic since it may end up leading to all code being collections of strings

        if(insertColumnName.equals(Course.getColumnName(Course.Columns.ID)))
        {
            parameters.addValue(parameterName, courseModel.getId());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.COURSE_NAME)))
        {
            parameters.addValue(parameterName, courseModel.getCourseName());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.COURSE_NUMBER)))
        {
            parameters.addValue(parameterName, courseModel.getCourseNumber());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.FREQUENCY)))
        {
            parameters.addValue(parameterName, courseModel.getFrequency());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.CREATED_AT)))
        {
            parameters.addValue(parameterName, courseModel.getCreatedAt());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.UPDATED_AT)))
        {
            parameters.addValue(parameterName, courseModel.getUpdatedAt());
        }
        else if(insertColumnName.equals(Course.getColumnName(Course.Columns.DELETED)))
        {
            parameters.addValue(parameterName, courseModel.getDeleted());
        }
        else
        {
            // should never end up here
            // lists should have already been validated
            throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
        }
    }

    private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, Course courseModel)
    {
        if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.ID)))
        {
            courseModel.setId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.COURSE_NAME)))
        {
            courseModel.setCourseName((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.COURSE_NUMBER)))
        {
            courseModel.setCourseNumber((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.FREQUENCY)))
        {
            courseModel.setFrequency((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.CREATED_AT)))
        {
            courseModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.UPDATED_AT)))
        {
            courseModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(Course.getColumnName(Course.Columns.DELETED)))
        {
            courseModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
        }
        else
        {
            // should never end up here
            // lists should have already been validated
            throw new IllegalArgumentException("Invalid column name provided: " + keyHolderColumnName);
        }
    }

    @Override
    public void validateColumnNames(List<String> columnNameList)
    {
        List<String> actualColumnNames = Course.getColumnNameList();
        boolean valid = actualColumnNames.containsAll(columnNameList);

        if(!valid)
        {
            List<String> invalidColumnNames = new ArrayList<>(columnNameList);
            invalidColumnNames.removeAll(actualColumnNames);

            throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
        }
    }

}


