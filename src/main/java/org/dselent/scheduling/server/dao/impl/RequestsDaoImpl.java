package org.dselent.scheduling.server.dao.impl;

import java.security.Policy.Parameters;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.RequestsDao;
import org.dselent.scheduling.server.extractor.RequestsExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Request;
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
public class RequestsDaoImpl extends BaseDaoImpl<Request> implements RequestsDao
{
	@Override
	public int insert(Request requestModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(Request.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, requestModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, requestModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, requestModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<Request> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		RequestsExtractor extractor = new RequestsExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(Request.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<Request> requestsList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return requestsList;
	}

	@Override
	public Request findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(Request.getColumnName(Request.Columns.ID), false);
		List<String> selectColumnNames = Request.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<Request> requestsList = select(selectColumnNames, queryTermList, orderByList);
	
	    Request request = null;
	    
	    if(!requestsList.isEmpty())
	    {
	    	request = requestsList.get(0);
	    }
	    
	    return request;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(Request.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(Request.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, Request requestModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(Request.getColumnName(Request.Columns.ID)))
    	{
    		parameters.addValue(parameterName, requestModel.getId());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.REQUESTER_ID)))
    	{
    		parameters.addValue(parameterName, requestModel.getRequesterId());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.REQUEST_TYPE)))
    	{
    		parameters.addValue(parameterName, requestModel.getRequestType());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.REQUEST_DETAILS)))
    	{
    		parameters.addValue(parameterName, requestModel.getRequestDetails());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.REPLY)))
    	{
    		parameters.addValue(parameterName, requestModel.getReply());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.REPLY_TYPE)))
    	{
    		parameters.addValue(parameterName, requestModel.getReplyType());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, requestModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, requestModel.getUpdatedAt());
    	}
    	else if(insertColumnName.equals(Request.getColumnName(Request.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, requestModel.getDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, Request requestModel)
	{
    	if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.ID)))
    	{
    		requestModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.REQUESTER_ID)))
    	{
    		requestModel.setRequesterId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.REQUEST_TYPE)))
    	{
    		requestModel.setRequestType((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.REQUEST_DETAILS)))
    	{
    		requestModel.setRequestDetails((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.REPLY)))
    	{
    		requestModel.setReply((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.REPLY_TYPE)))
    	{
    		requestModel.setReplyType((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.CREATED_AT)))
    	{
    		requestModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.UPDATED_AT)))
    	{
    		requestModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Request.getColumnName(Request.Columns.DELETED)))
    	{
    		requestModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = Request.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
