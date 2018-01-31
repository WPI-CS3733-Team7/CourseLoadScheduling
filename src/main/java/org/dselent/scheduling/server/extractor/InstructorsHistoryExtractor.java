package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.InstructorHistory;

public class InstructorsHistoryExtractor extends Extractor<List<InstructorHistory>>{

	@Override
	public List<InstructorHistory> extractData(ResultSet rs) throws SQLException {
		
		List<InstructorHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			InstructorHistory result = new InstructorHistory();
				
			result.setId(rs.getInt(InstructorHistory.getColumnName(InstructorHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(InstructorHistory.getColumnName(InstructorHistory.Columns.FORMER_ID)));
			if(rs.wasNull())
			{
				result.setFormerId(null);
			}
			
			result.setRank(rs.getString(InstructorHistory.getColumnName(InstructorHistory.Columns.RANK)));
			result.setFirstName(rs.getString(InstructorHistory.getColumnName(InstructorHistory.Columns.FIRST_NAME)));
			result.setLastName(rs.getString(InstructorHistory.getColumnName(InstructorHistory.Columns.LAST_NAME)));
			result.setEmail(rs.getString(InstructorHistory.getColumnName(InstructorHistory.Columns.EMAIL)));
			
			result.setCreatedAt(rs.getTimestamp(InstructorHistory.getColumnName(InstructorHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;	
	}

}