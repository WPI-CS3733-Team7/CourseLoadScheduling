package org.dselent.scheduling.server.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.extractor.UserRolesExtractor;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.UserRole;
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
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRolesDao
{
	@Override
	public int insert(UserRole userRoleModel, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException
	{
		
		validateColumnNames(insertColumnNameList);
		validateColumnNames(keyHolderColumnNameList);

		String queryTemplate = QueryStringBuilder.generateInsertString(UserRole.TABLE_NAME, insertColumnNameList);
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    
	    List<Map<String, Object>> keyList = new ArrayList<>();
	    KeyHolder keyHolder = new GeneratedKeyHolder(keyList);
	    
	    for(String insertColumnName : insertColumnNameList)
	    {
	    	addParameterMapValue(parameters, insertColumnName, userRoleModel);
	    }
	    // new way, but unfortunately unnecessary class creation is slow and wasteful (i.e. wrong)
	    // insertColumnNames.forEach(insertColumnName -> addParameterMap(parameters, insertColumnName, userRoleModel));
	    
	    int rowsAffected = namedParameterJdbcTemplate.update(queryTemplate, parameters, keyHolder);
	    
	    Map<String, Object> keyMap = keyHolder.getKeys();
	    
	    for(String keyHolderColumnName : keyHolderColumnNameList)
	    {
	    	addObjectValue(keyMap, keyHolderColumnName, userRoleModel);
	    }
	    	    
	    return rowsAffected;
		
	}
	
	
	@Override
	public List<UserRole> select(List<String> selectColumnNameList, List<QueryTerm> queryTermList, List<Pair<String, ColumnOrder>> orderByList) throws SQLException
	{
		UserRolesExtractor extractor = new UserRolesExtractor();
		String queryTemplate = QueryStringBuilder.generateSelectString(UserRole.TABLE_NAME, selectColumnNameList, queryTermList, orderByList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    List<UserRole> userRoleList = jdbcTemplate.query(queryTemplate, extractor, parameters);
	    
	    return userRoleList;
	}

	@Override
	public UserRole findById(int id) throws SQLException
	{
		String columnName = QueryStringBuilder.convertColumnName(UserRole.getColumnName(UserRole.Columns.ID), false);
		List<String> selectColumnNames = UserRole.getColumnNameList();
		
		List<QueryTerm> queryTermList = new ArrayList<>();
		QueryTerm idTerm = new QueryTerm(columnName, ComparisonOperator.EQUAL, id, null);
		queryTermList.add(idTerm);
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> order = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
		orderByList.add(order);
		
		List<UserRole> userRoleList = select(selectColumnNames, queryTermList, orderByList);
	
	    UserRole userRole = null;
	    
	    if(!userRoleList.isEmpty())
	    {
	    	userRole = userRoleList.get(0);
	    }
	    
	    return userRole;
	}
	
	@Override
	public int update(String columnName, Object newValue, List<QueryTerm> queryTermList)
	{
		String queryTemplate = QueryStringBuilder.generateUpdateString(UserRole.TABLE_NAME, columnName, queryTermList);

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
		String queryTemplate = QueryStringBuilder.generateDeleteString(UserRole.TABLE_NAME, queryTermList);

		List<Object> objectList = new ArrayList<Object>();
		
		for(QueryTerm queryTerm : queryTermList)
		{
			objectList.add(queryTerm.getValue());
		}
		
	    Object[] parameters = objectList.toArray();
		 
	    int rowsAffected = jdbcTemplate.update(queryTemplate, parameters);
	    
		return rowsAffected;
	}

	private void addParameterMapValue(MapSqlParameterSource parameters, String insertColumnName, UserRole userRoleModel)
	{
		String parameterName = QueryStringBuilder.convertColumnName(insertColumnName, false);
    	
    	// Wish this could generalize
    	// The getter must be distinguished unless the models are designed as simply a map of columns-values
    	// Would prefer not being that generic since it may end up leading to all code being collections of strings
		
    	if(insertColumnName.equals(UserRole.getColumnName(UserRole.Columns.ID)))
    	{
    		parameters.addValue(parameterName, userRoleModel.getId());
    	}
    	else if(insertColumnName.equals(UserRole.getColumnName(UserRole.Columns.ROLE_NAME)))
    	{
    		parameters.addValue(parameterName, userRoleModel.getRoleName());
    	}
    	else if(insertColumnName.equals(UserRole.getColumnName(UserRole.Columns.CREATED_AT)))
    	{
    		parameters.addValue(parameterName, userRoleModel.getCreatedAt());
    	}
    	else if(insertColumnName.equals(UserRole.getColumnName(UserRole.Columns.UPDATED_AT)))
    	{
    		parameters.addValue(parameterName, userRoleModel.getUpdatedAt());
    	}
    	else if(insertColumnName.equals(UserRole.getColumnName(UserRole.Columns.DELETED)))
    	{
    		parameters.addValue(parameterName, userRoleModel.isDeleted());
    	}
    	else
    	{
    		// should never end up here
    		// lists should have already been validated
    		throw new IllegalArgumentException("Invalid column name provided: " + insertColumnName);
    	}
	}	

	private void addObjectValue(Map<String, Object> keyMap, String keyHolderColumnName, UserRole userRoleModel)
	{
    	if(keyHolderColumnName.equals(UserRole.getColumnName(UserRole.Columns.ID)))
    	{
    		userRoleModel.setId((Integer) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(UserRole.getColumnName(UserRole.Columns.ROLE_NAME)))
    	{
    		userRoleModel.setRoleName((String) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(UserRole.getColumnName(UserRole.Columns.CREATED_AT)))
    	{
    		userRoleModel.setCreatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(UserRole.getColumnName(UserRole.Columns.UPDATED_AT)))
    	{
    		userRoleModel.setUpdatedAt((Timestamp) keyMap.get(keyHolderColumnName));
    	}
    	else if(keyHolderColumnName.equals(UserRole.getColumnName(UserRole.Columns.DELETED)))
    	{
    		userRoleModel.setDeleted((boolean) keyMap.get(keyHolderColumnName));
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
		List<String> actualColumnNames = UserRole.getColumnNameList();
		boolean valid = actualColumnNames.containsAll(columnNameList);
		
		if(!valid)
		{
			List<String> invalidColumnNames = new ArrayList<>(columnNameList);
			invalidColumnNames.removeAll(actualColumnNames);
			
			throw new IllegalArgumentException("Invalid column names provided: " + invalidColumnNames);
		}
	}
}

