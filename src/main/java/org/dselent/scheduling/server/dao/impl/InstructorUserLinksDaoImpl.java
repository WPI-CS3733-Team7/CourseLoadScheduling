package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.extractor.InstructorUserLinksExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.miscellaneous.QueryStringBuilder;
import org.dselent.scheduling.server.model.InstructorUserLink;
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
public class InstructorUserLinksDaoImpl extends BaseDaoImpl<InstructorUserLink> implements InstructorUserLinksDao
{
	@Override
	public int insert(InstructorUserLink instructorUserLinkModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(InstructorUserLink.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, instructorUserLinkModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, instructorUserLinkModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, instructorUserLinkModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<InstructorUserLink> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		InstructorUserLinksExtractor extractor = new InstructorUserLinksExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(InstructorUserLink.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<InstructorUserLink> instructorUserLinkList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return instructorUserLinkList;
	}

	@Override
	public InstructorUserLink findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(InstructorUserLink.getColumnName(InstructorUserLink.Columns.ID), false);
		List<String> selectColumnNames = InstructorUserLink.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<InstructorUserLink> instructorUserLinkList = select(selectColumnNames, queryTermList, orderByList);
	
	    InstructorUserLink instructorUserLink = null;
	    
	    if(!instructorUserLinkList.isEmpty())
	    {
	    	instructorUserLink = instructorUserLinkList.get(0);
	    }
	    
	    return instructorUserLink;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(InstructorUserLink.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(InstructorUserLink.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, InstructorUserLink instructorUserLinkModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getId());
    	}
    	else if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getInstructorId());
    	}
    	else if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getLinkedUserId());
    	}
    	else if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getUpdatedAt());
    	}
    	else if(insertColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, instructorUserLinkModel.getDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, InstructorUserLink instructorUserLinkModel)
	{
    	if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.ID)))
    	{
    		instructorUserLinkModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID)))
    	{
    		instructorUserLinkModel.setInstructorId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID)))
    	{
    		instructorUserLinkModel.setLinkedUserId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.CREATED_AT)))
    	{
    		instructorUserLinkModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.UPDATED_AT)))
    	{
    		instructorUserLinkModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(InstructorUserLink.getColumnName(InstructorUserLink.Columns.DELETED)))
    	{
    		instructorUserLinkModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = InstructorUserLink.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
