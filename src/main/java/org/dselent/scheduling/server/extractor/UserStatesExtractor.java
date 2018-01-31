package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.UserState;

public class UserStatesExtractor extends Extractor<List<UserState>>{

	@Override
	public List<UserState> extractData(ResultSet rs) throws SQLException {
		List<UserState> resultList = new ArrayList<>();

		while(rs.next())
		{
			UserState result = new UserState();
				
			result.setId(rs.getInt(UserState.getColumnName(UserState.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}

			result.setState(rs.getString(UserState.getColumnName(UserState.Columns.STATE)));
			
			result.setCreatedAt(rs.getTimestamp(UserState.getColumnName(UserState.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(UserState.getColumnName(UserState.Columns.UPDATED_AT)));;
		
			result.setDeleted(rs.getBoolean(UserState.getColumnName(UserState.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
			
			resultList.add(result);
		}
			
		return resultList;
	}

}
