package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.InstructorUserLinkHistory;

public class InstructorUserLinksHistoryExtractor extends Extractor<List<InstructorUserLinkHistory>>{

	@Override
	public List<InstructorUserLinkHistory> extractData(ResultSet rs) throws SQLException {
		
		List<InstructorUserLinkHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			InstructorUserLinkHistory result = new InstructorUserLinkHistory();
				
			result.setId(rs.getInt(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.FORMER_ID)));
			
			result.setInstructorId(rs.getInt(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.INSTRUCTOR_ID)));
			if(rs.wasNull())
			{
				result.setInstructorId(null);
			}
			
			result.setLinkedUserId(rs.getInt(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.LINKED_USER_ID)));
			if(rs.wasNull())
			{
				result.setLinkedUserId(null);
			}
		
			result.setCreatedAt(rs.getTimestamp(InstructorUserLinkHistory.getColumnName(InstructorUserLinkHistory.Columns.CREATED_AT)));

			resultList.add(result);
		}
			
		return resultList;	
	}
	
}
