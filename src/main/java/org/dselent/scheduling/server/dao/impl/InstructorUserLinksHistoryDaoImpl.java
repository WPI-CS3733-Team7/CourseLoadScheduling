package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.InstructorUserLinksHistoryDao;
import org.dselent.scheduling.server.extractor.InstructorUserLinksHistoryExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorUserLinkHistory;
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
public class InstructorUserLinksHistoryDaoImpl extends BaseDaoImpl<InstructorUserLinkHistory> implements InstructorUserLinksHistoryDao
{
	@Override
	public int insert(InstructorUserLinkHistory instructorUserLinkHistoryModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(InstructorUserLinkHistory.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, instructorUserLinkHistoryModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, instructorUserLinkHistoryModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, instructorUserLinkHistoryModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<InstructorUserLinkHistory> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		InstructorUserLinksHistoryExtractor extractor = new InstructorUserLinksHistoryExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(InstructorUserLinkHistory.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<InstructorUserLinkHistory> instructorUserLinkHistoryList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return instructorUserLinkHistoryList;
	}

	@Override
	public InstructorUserLinkHistory findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.ID), false);
		List<String> selectColumnNames = InstructorUserLinkHistory.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<InstructorUserLinkHistory> instructorUserLinkHistoryList = select(selectColumnNames, queryTermList, orderByList);
	
	    InstructorUserLinkHistory instructorUserLinkHistory = null;
	    
	    if(!instructorUserLinkHistoryList.isEmpty())
	    {
	    	instructorUserLinkHistory = instructorUserLinkHistoryList.get(0);
	    }
	    
	    return instructorUserLinkHistory;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(InstructorUserLinkHistory.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(InstructorUserLinkHistory.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, InstructorUserLinkHistory instructorUserLinkHistoryModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkHistoryModel.getId());
    	}
    	else if(insertColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.FORMER_ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkHistoryModel.getFormerId());
    	}
    	else if(insertColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkHistoryModel.getInstructorId());
    	}
    	else if(insertColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.LINKED_USER_ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkHistoryModel.getLinkedUserId());
    	}
    	else if(insertColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkHistoryModel.getCreatedAt());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, InstructorUserLinkHistory instructorUserLinkHistoryModel)
	{
    	if(keyHolderColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.ID)))
    	{
    		instructorUserLinkHistoryModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.FORMER_ID)))
    	{
    		instructorUserLinkHistoryModel.setFormerId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID)))
    	{
    		instructorUserLinkHistoryModel.setInstructorId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.LINKED_USER_ID)))
    	{
    		instructorUserLinkHistoryModel.setLinkedUserId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.CREATED_AT)))
    	{
    		instructorUserLinkHistoryModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = InstructorUserLinkHistory.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
