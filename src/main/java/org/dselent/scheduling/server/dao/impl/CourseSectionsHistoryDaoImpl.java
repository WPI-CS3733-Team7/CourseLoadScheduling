package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.extractor.CourseSectionsHistoryExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.CourseSectionHistory;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseSectionsHistoryDaoImpl extends BaseDaoImpl<CourseSectionHistory> implements CourseSectionsDao{
    @Override
    public int insert(CourseSectionHistory courseSectionHistoryModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
    {

        validateColumnNames(insertColumnNameList);
        validateColumnNames(keyHolderColumnNameList);

        String queryTemplate = QueryStringBuilder.generateInsertString(CourseSectionHistory.TABLE_NAME, insertColumnNameList);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        List<Map<String, Object>> keyList = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        for(String insertColumnName : insertColumnNameList)
        {
            addParameterMapValue(parameters, insertColumnName, courseSectionHistoryModel);
        }
        // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
        // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, courseSectionHistoryModel));

        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        Map<String, Object> keyMap = keyHolder.getKeys();

        for(String keyHolderColumnName : keyHolderColumnNameList)
        {
            addObjectValue(keyMap, keyHolderColumnName, courseSectionHistoryModel);
        }

        return rowsAffected;

    }

    @Override
    public List<CourseSectionHistory> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
    {
        CourseSectionsHistoryExtractor extractor = new CourseSectionsHistoryExtractor();
        String queryTemplate = QueryStringBuilder.generateSelectString(CourseSectionHistory.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        List<CourseSectionHistory> courseSectionHistoryList = jdbcTemplate.query(queryTemplate, extractor, parameters);

        return courseSectionHistoryList;
    }

    @Override
    public CourseSectionHistory findById(int id) throws SQLException
    {
        String columnName = QueryStringBuilder.convertColumnName(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.ID), false);
        List<String> selectColumnNames = CourseSectionHistory.getColumnNameList();

        List<QueryTerm> queryTermList = new ArrayList<>();
        QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
        queryTermList.add(idTerm);

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
        orderByList.add(order);

        List<CourseSectionHistory> courseSectionHistoryList = select(selectColumnNames, queryTermList, orderByList);

        CourseSectionHistory courseSectionHistory = null;

        if(!courseSectionHistoryList.isEmpty())
        {
            courseSectionHistory = courseSectionHistoryList.get(0);
        }

        return courseSectionHistory;
    }

    @Override
    public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
    {
        String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSectionHistory.TABLE_NAME, columnName, queryTermList);

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
        String queryTemplate = QueryStringBuilder.generateDeleteString(CourseSectionHistory.TABLE_NAME, queryTermList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);

        return rowsAffected;
    }

    private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseSectionHistory courseSectionHistoryModel)
    {
        String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);

        // Wish this could generalize
        // The getter must be distinguished unless the models are designed as simply a map of columns-values
        // Would prefer not being that generic since it may end up leading to all code being collections of strings

        if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.FORMER_ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getFormerId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getSectionName());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getSectionId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_TYPE)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getSectionType());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.POPULATION)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getPopulation());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.COURSE_ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getCourseId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.INSTRUCTOR_ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getInstructorId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CALENDAR_INFO_ID)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getCalendarInfoId());
        }
        else if(insertColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CREATED_AT)))
        {
            parameters.addValue(parameterName, courseSectionHistoryModel.getCreatedAt());
        }
        else
        {
            // should never end up here
            // lists should have already been validated
            throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
        }
    }

    private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseSectionHistory courseSectionHistoryModel)
    {
        if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.ID)))
        {
            courseSectionHistoryModel.setId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.FORMER_ID)))
        {
            courseSectionHistoryModel.setFormerId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME)))
        {
            courseSectionHistoryModel.setSectionName((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_ID)))
        {
            courseSectionHistoryModel.setSectionId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_TYPE)))
        {
            courseSectionHistoryModel.setSectionType((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.POPULATION)))
        {
            courseSectionHistoryModel.setPopulation((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.COURSE_ID)))
        {
            courseSectionHistoryModel.setCourseId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.INSTRUCTOR_ID)))
        {
            courseSectionHistoryModel.setInstructorId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CALENDAR_INFO_ID)))
        {
            courseSectionHistoryModel.setCalendarInfoId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CREATED_AT)))
        {
            courseSectionHistoryModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
        List<String> actualColumnNames = CourseSectionHistory.getColumnNameList();
        boolean valid = actualColumnNames.containsAll(columnNameList);

        if(!valid)
        {
            List<String> invalidColumnNames = new ArrayList<>(columnNameList);
            invalidColumnNames.removeAll(actualColumnNames);

            throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
        }
    }
}
