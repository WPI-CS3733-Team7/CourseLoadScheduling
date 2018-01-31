package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.Instructor;

public class InstructorsExtractor extends Extractor<List<Instructor>>{

	@Override
	public List<Instructor> extractData(ResultSet rs) throws SQLException {
		
		List<Instructor> resultList = new ArrayList<>();

		while(rs.next())
		{
			Instructor result = new Instructor();
				
			result.setId(rs.getInt(Instructor.getColumnName(Instructor.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setRank(rs.getString(Instructor.getColumnName(Instructor.Columns.RANK)));
			result.setFirstName(rs.getString(Instructor.getColumnName(Instructor.Columns.FIRST_NAME)));
			result.setLastName(rs.getString(Instructor.getColumnName(Instructor.Columns.LAST_NAME)));
			result.setEmail(rs.getString(Instructor.getColumnName(Instructor.Columns.EMAIL)));
			
			result.setCreatedAt(rs.getTimestamp(Instructor.getColumnName(Instructor.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(Instructor.getColumnName(Instructor.Columns.UPDATED_AT)));
			
			result.setDeleted(rs.getBoolean(Instructor.getColumnName(Instructor.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;	
	}

}
