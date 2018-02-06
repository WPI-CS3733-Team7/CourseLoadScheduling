package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CalendarInfoHistoryDao;
import org.dselent.scheduling.server.extractor.CalendarInfoHistoryExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfoHistory;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryStringBuilder;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


/*
 * @Repository annotation
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html
 * 
 * Useful link
 * https://howtodoinjava.com/spring/spring-core/how-to-use-spring-component-repository-service-and-controller-annotations/
 */
@Repository
public class CalendarInfoHistoryDaoImpl extends BaseDaoImpl<CalendarInfoHistory> implements CalendarInfoHistoryDao
{
	@Override
	public int insert(CalendarInfoHistory calendarInfoHistoryModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CalendarInfoHistory.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, calendarInfoHistoryModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, calendarInfoHistoryModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, calendarInfoHistoryModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CalendarInfoHistory> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CalendarInfoHistoryExtractor extractor = new CalendarInfoHistoryExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CalendarInfoHistory.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CalendarInfoHistory> calendarInfoHistoryList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return calendarInfoHistoryList;
	}

	@Override
	public CalendarInfoHistory findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.ID), false);
		List<String> selectColumnNames = CalendarInfoHistory.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CalendarInfoHistory> calendarInfoHistoryList = select(selectColumnNames, queryTermList, orderByList);
	
	    CalendarInfoHistory calendarInfoHistory = null;
	    
	    if(!calendarInfoHistoryList.isEmpty())
	    {
	    	calendarInfoHistory = calendarInfoHistoryList.get(0);
	    }
	    
	    return calendarInfoHistory;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CalendarInfoHistory.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CalendarInfoHistory.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CalendarInfoHistory calendarInfoHistoryModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.ID)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getId());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.FORMER_ID)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getFormerId());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_YEAR)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getCalYear());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getCalTerm());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.DAYS)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getDays());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.START_TIME)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getStartTime());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.END_TIME)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getEndTime());
    	}
    	else if(insertColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, calendarInfoHistoryModel.getCreatedAt());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CalendarInfoHistory calendarInfoHistoryModel)
	{
    	if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.ID)))
    	{
    		calendarInfoHistoryModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.FORMER_ID)))
    	{
    		calendarInfoHistoryModel.setFormerId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_YEAR)))
    	{
    		calendarInfoHistoryModel.setCalYear((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM)))
    	{
    		calendarInfoHistoryModel.setCalTerm((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.DAYS)))
    	{
    		calendarInfoHistoryModel.setDays((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.START_TIME)))
    	{
    		calendarInfoHistoryModel.setStartTime((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.END_TIME)))
    	{
    		calendarInfoHistoryModel.setEndTime((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CREATED_AT)))
    	{
    		calendarInfoHistoryModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CalendarInfoHistory.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}