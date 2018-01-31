package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.InstructorsHistoryDao;
import org.dselent.scheduling.server.extractor.InstructorsHistoryExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.InstructorHistory;
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
public class InstructorsHistoryDaoImpl extends BaseDaoImpl<InstructorHistory> implements InstructorsHistoryDao
{
	@Override
	public int insert(InstructorHistory instructorHistoryModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(InstructorHistory.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, instructorHistoryModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, instructorHistoryModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, instructorHistoryModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<InstructorHistory> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		InstructorsHistoryExtractor extractor = new InstructorsHistoryExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(InstructorHistory.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<InstructorHistory> instructorHistoryList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return instructorHistoryList;
	}

	@Override
	public InstructorHistory findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(InstructorHistory.getColumnName(InstructorHistory.Columns.ID), false);
		List<String> selectColumnNames = InstructorHistory.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<InstructorHistory> instructorHistoryList = select(selectColumnNames, queryTermList, orderByList);
	
	    InstructorHistory instructorHistory = null;
	    
	    if(!instructorHistoryList.isEmpty())
	    {
	    	instructorHistory = instructorHistoryList.get(0);
	    }
	    
	    return instructorHistory;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(InstructorHistory.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(InstructorHistory.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, InstructorHistory instructorHistoryModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.ID)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getId());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.FORMER_ID)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getFormerId());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.RANK)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getRank());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getFirstName());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.LAST_NAME)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getLastName());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.EMAIL)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getEmail());
    	}
    	else if(insertColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorHistoryModel.getCreatedAt());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, InstructorHistory instructorHistoryModel)
	{
    	if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.ID)))
    	{
    		instructorHistoryModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.FORMER_ID)))
    	{
    		instructorHistoryModel.setFormerId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.RANK)))
    	{
    		instructorHistoryModel.setRank((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME)))
    	{
    		instructorHistoryModel.setFirstName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.LAST_NAME)))
    	{
    		instructorHistoryModel.setLastName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.EMAIL)))
    	{
    		instructorHistoryModel.setEmail((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorHistory.getColumnName(InstructorHistory.Columns.CREATED_AT)))
    	{
    		instructorHistoryModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = InstructorHistory.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
