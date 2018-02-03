package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.extractor.CourseSectionsExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseSectionsDaoImpl extends BaseDaoImpl<CourseSection> implements CourseSectionsDao {
    @Override
    public int insert(CourseSection courseSectionModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
    {

        validateColumnNames(insertColumnNameList);
        validateColumnNames(keyHolderColumnNameList);

        String queryTemplate = QueryStringBuilder.generateInsertString(CourseSection.TABLE_NAME, insertColumnNameList);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        List<Map<String, Object>> keyList = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder(keyList);

        for(String insertColumnName : insertColumnNameList)
        {
            addParameterMapValue(parameters, insertColumnName, courseSectionModel);
        }
        // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
        // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, courseSectionModel));

        int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);

        Map<String, Object> keyMap = keyHolder.getKeys();

        for(String keyHolderColumnName : keyHolderColumnNameList)
        {
            addObjectValue(keyMap, keyHolderColumnName, courseSectionModel);
        }

        return rowsAffected;

    }

    @Override
    public List<CourseSection> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
    {
        CourseSectionsExtractor extractor = new CourseSectionsExtractor();
        String queryTemplate = QueryStringBuilder.generateSelectString(CourseSection.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        List<CourseSection> courseSectionList = jdbcTemplate.query(queryTemplate, extractor, parameters);

        return courseSectionList;
    }

    @Override
    public CourseSection findById(int id) throws SQLException
    {
        String columnName = QueryStringBuilder.convertColumnName(CourseSection.getColumnName(CourseSection.Columns.ID), false);
        List<String> selectColumnNames = CourseSection.getColumnNameList();

        List<QueryTerm> queryTermList = new ArrayList<>();
        QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
        queryTermList.add(idTerm);

        List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
        Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
        orderByList.add(order);

        List<CourseSection> courseSectionList = select(selectColumnNames, queryTermList, orderByList);

        CourseSection courseSection = null;

        if(!courseSectionList.isEmpty())
        {
            courseSection = courseSectionList.get(0);
        }

        return courseSection;
    }

    @Override
    public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
    {
        String queryTemplate = QueryStringBuilder.generateUpdateString(CourseSection.TABLE_NAME, columnName, queryTermList);

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
        String queryTemplate = QueryStringBuilder.generateDeleteString(CourseSection.TABLE_NAME, queryTermList);

        List<Object> objectList = new ArrayList<Object>();

        for(QueryTerm queryTerm : queryTermList)
        {
            objectList.add(queryTerm.getValue());
        }

        Object[] parameters = objectList.toArray();

        int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);

        return rowsAffected;
    }

    private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CourseSection courseSectionModel)
    {
        String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);

        // Wish this could generalize
        // The getter must be distinguished unless the models are designed as simply a map of columns-values
        // Would prefer not being that generic since it may end up leading to all code being collections of strings

        if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.ID)))
        {
            parameters.addValue(parameterName, courseSectionModel.getId());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME)))
        {
            parameters.addValue(parameterName, courseSectionModel.getSectionName());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_ID)))
        {
            parameters.addValue(parameterName, courseSectionModel.getSectionId());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE)))
        {
            parameters.addValue(parameterName, courseSectionModel.getSectionType());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.POPULATION)))
        {
            parameters.addValue(parameterName, courseSectionModel.getPopulation());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID)))
        {
            parameters.addValue(parameterName, courseSectionModel.getCourseId());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID)))
        {
            parameters.addValue(parameterName, courseSectionModel.getInstructorId());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CALENDAR_INFO_ID)))
        {
            parameters.addValue(parameterName, courseSectionModel.getCalendarInfoId());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)))
        {
            parameters.addValue(parameterName, courseSectionModel.getCreatedAt());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)))
        {
            parameters.addValue(parameterName, courseSectionModel.getUpdatedAt());
        }
        else if(insertColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.DELETED)))
        {
            parameters.addValue(parameterName, courseSectionModel.getDeleted());
        }
        else
        {
            // should never end up here
            // lists should have already been validated
            throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
        }
    }

    private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CourseSection courseSectionModel)
    {
        if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.ID)))
        {
            courseSectionModel.setId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME)))
        {
            courseSectionModel.setSectionName((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_ID)))
        {
            courseSectionModel.setSectionId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE)))
        {
            courseSectionModel.setSectionType((String) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.POPULATION)))
        {
            courseSectionModel.setPopulation((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID)))
        {
            courseSectionModel.setCourseId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID)))
        {
            courseSectionModel.setInstructorId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CALENDAR_INFO_ID)))
        {
            courseSectionModel.setCalendarInfoId((Integer) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)))
        {
            courseSectionModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)))
        {
            courseSectionModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
        }
        else if(keyHolderColumnName.equals(CourseSection.getColumnName(CourseSection.Columns.DELETED)))
        {
            courseSectionModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
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
        List<String> actualColumnNames = CourseSection.getColumnNameList();
        boolean valid = actualColumnNames.containsAll(columnNameList);

        if(!valid)
        {
            List<String> invalidColumnNames = new ArrayList<>(columnNameList);
            invalidColumnNames.removeAll(actualColumnNames);

            throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
        }
    }
}
