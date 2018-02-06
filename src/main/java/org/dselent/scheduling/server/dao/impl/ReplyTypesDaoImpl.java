package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.ReplyTypesDao;
import org.dselent.scheduling.server.extractor.ReplyTypesExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.ReplyType;
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
public class ReplyTypesDaoImpl extends BaseDaoImpl<ReplyType> implements ReplyTypesDao
{
	@Override
	public int insert(ReplyType replyTypeModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(ReplyType.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, replyTypeModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, replyTypeModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, replyTypeModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<ReplyType> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		ReplyTypesExtractor extractor = new ReplyTypesExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(ReplyType.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<ReplyType> replyTypesList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return replyTypesList;
	}

	@Override
	public ReplyType findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(ReplyType.getColumnName(ReplyType.Columns.ID), false);
		List<String> selectColumnNames = ReplyType.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<ReplyType> replyTypesList = select(selectColumnNames, queryTermList, orderByList);
	
	    ReplyType replyType = null;
	    
	    if(!replyTypesList.isEmpty())
	    {
	    	replyType = replyTypesList.get(0);
	    }
	    
	    return replyType;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(ReplyType.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(ReplyType.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, ReplyType replyTypeModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.ID)))
    	{
    		parameters.addValue(parameterName, replyTypeModel.getId());
    	}
    	else if(insertColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE)))
    	{
    		parameters.addValue(parameterName, replyTypeModel.getReplyType());
    	}
    	else if(insertColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, replyTypeModel.getDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, ReplyType replyTypeModel)
	{
    	if(keyHolderColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.ID)))
    	{
    		replyTypeModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE)))
    	{
    		replyTypeModel.setReplyType((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(ReplyType.getColumnName(ReplyType.Columns.DELETED)))
    	{
    		replyTypeModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = ReplyType.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
