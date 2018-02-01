package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.InstructorUserLink;

public class InstructorUserLinksExtractor extends Extractor<List<InstructorUserLink>>{

	@Override
	public List<InstructorUserLink> extractData(ResultSet rs) throws SQLException {
		
		List<InstructorUserLink> resultList = new ArrayList<>();

		while(rs.next())
		{
			InstructorUserLink result = new InstructorUserLink();
				
			result.setId(rs.getInt(InstructorUserLink.getColumnName(InstructorUserLink.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setInstructorId(rs.getInt(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID)));
			if(rs.wasNull())
			{
				result.setInstructorId(null);
			}
			
			result.setLinkedUserId(rs.getInt(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID)));
			if(rs.wasNull())
			{
				result.setLinkedUserId(null);
			}
		
			result.setCreatedAt(rs.getTimestamp(InstructorUserLink.getColumnName(InstructorUserLink.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(InstructorUserLink.getColumnName(InstructorUserLink.Columns.UPDATED_AT)));
			
			result.setDeleted(rs.getBoolean(InstructorUserLink.getColumnName(InstructorUserLink.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;	
	}

}
