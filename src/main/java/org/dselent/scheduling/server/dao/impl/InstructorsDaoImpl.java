package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.extractor.InstructorsExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Instructor;
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
public class InstructorsDaoImpl extends BaseDaoImpl<Instructor> implements InstructorsDao
{
	@Override
	public int insert(Instructor instructorModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(Instructor.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, instructorModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, instructorModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, instructorModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<Instructor> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		InstructorsExtractor extractor = new InstructorsExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(Instructor.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<Instructor> instructorList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return instructorList;
	}

	@Override
	public Instructor findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(Instructor.getColumnName(Instructor.Columns.ID), false);
		List<String> selectColumnNames = Instructor.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<Instructor> instructorList = select(selectColumnNames, queryTermList, orderByList);
	
	    Instructor instructor = null;
	    
	    if(!instructorList.isEmpty())
	    {
	    	instructor = instructorList.get(0);
	    }
	    
	    return instructor;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(Instructor.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(Instructor.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, Instructor instructorModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.ID)))
    	{
    		parameters.addValue(parameterName, instructorModel.getId());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.RANK)))
    	{
    		parameters.addValue(parameterName, instructorModel.getRank());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.FIRST_NAME)))
    	{
    		parameters.addValue(parameterName, instructorModel.getFirstName());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.LAST_NAME)))
    	{
    		parameters.addValue(parameterName, instructorModel.getLastName());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.EMAIL)))
    	{
    		parameters.addValue(parameterName, instructorModel.getEmail());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, instructorModel.getUpdatedAt());
    	}
    	else if(insertColumnName.equals(Instructor.getColumnName(Instructor.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, instructorModel.getDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, Instructor instructorModel)
	{
    	if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.ID)))
    	{
    		instructorModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.RANK)))
    	{
    		instructorModel.setRank((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.FIRST_NAME)))
    	{
    		instructorModel.setFirstName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.LAST_NAME)))
    	{
    		instructorModel.setLastName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.EMAIL)))
    	{
    		instructorModel.setEmail((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.CREATED_AT)))
    	{
    		instructorModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.UPDATED_AT)))
    	{
    		instructorModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(Instructor.getColumnName(Instructor.Columns.DELETED)))
    	{
    		instructorModel.setDeleted((Boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = Instructor.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}
