package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseLoadHistory;

public class CourseLoadsHistoryExtractor extends Extractor<List<CourseLoadHistory>> {

	@Override
	public List<CourseLoadHistory> extractData(ResultSet rs) throws SQLException {
		
		List<CourseLoadHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseLoadHistory result = new CourseLoadHistory();
				
			result.setId(rs.getInt(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.FORMER_ID)));
			if(rs.wasNull())
			{
				result.setFormerId(null);
			}
			
			result.setLoadType(rs.getString(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_TYPE)));
			result.setLoadDescription(rs.getString(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.LOAD_DESCRIPTION)));
			
			result.setInstructorId(rs.getInt(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.INSTRUCTOR_ID)));
			if(rs.wasNull())
			{
				result.setInstructorId(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;	
	}

}