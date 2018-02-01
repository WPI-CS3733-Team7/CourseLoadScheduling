package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.extractor.CalendarInfoExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
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
public class CalendarInfoDaoImpl extends BaseDaoImpl<CalendarInfo> implements CalendarInfoDao
{
	@Override
	public int insert(CalendarInfo calendarInfoModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(CalendarInfo.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, calendarInfoModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, calendarInfoModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, calendarInfoModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<CalendarInfo> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		CalendarInfoExtractor extractor = new CalendarInfoExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(CalendarInfo.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<CalendarInfo> calendarInfoList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return calendarInfoList;
	}

	@Override
	public CalendarInfo findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.ID), false);
		List<String> selectColumnNames = CalendarInfo.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<CalendarInfo> calendarInfoList = select(selectColumnNames, queryTermList, orderByList);
	
	    CalendarInfo calendarInfo = null;
	    
	    if(!calendarInfoList.isEmpty())
	    {
	    	calendarInfo = calendarInfoList.get(0);
	    }
	    
	    return calendarInfo;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(CalendarInfo.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(CalendarInfo.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, CalendarInfo calendarInfoModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.ID)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getId());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getCalYear());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getCalTerm());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getDays());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getStartTime());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getEndTime());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getUpdatedAt());
    	}
    	else if(insertColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, calendarInfoModel.getDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, CalendarInfo calendarInfoModel)
	{
    	if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.ID)))
    	{
    		calendarInfoModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR)))
    	{
    		calendarInfoModel.setCalYear((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM)))
    	{
    		calendarInfoModel.setCalTerm((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS)))
    	{
    		calendarInfoModel.setDays((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME)))
    	{
    		calendarInfoModel.setStartTime((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME)))
    	{
    		calendarInfoModel.setEndTime((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.CREATED_AT)))
    	{
    		calendarInfoModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.UPDATED_AT)))
    	{
    		calendarInfoModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED)))
    	{
    		calendarInfoModel.setDeleted((boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = CalendarInfo.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}