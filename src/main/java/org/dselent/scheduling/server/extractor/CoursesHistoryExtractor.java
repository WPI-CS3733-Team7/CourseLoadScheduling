package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseHistory;

public class CoursesHistoryExtractor extends Extractor<List<CourseHistory>>{

	@Override
	public List<CourseHistory> extractData(ResultSet rs) throws SQLException
	{
		List<CourseHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseHistory result = new CourseHistory();
				
			result.setId(rs.getInt(CourseHistory.getColumnName(CourseHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(CourseHistory.getColumnName(CourseHistory.Columns.FORMER_ID)));
			if(rs.wasNull())
			{
				result.setFormerId(null);
			}
			
			result.setCourseName(rs.getString(CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NAME)));
			result.setCourseNumber(rs.getString(CourseHistory.getColumnName(CourseHistory.Columns.COURSE_NUMBER)));
			result.setFrequency(rs.getString(CourseHistory.getColumnName(CourseHistory.Columns.FREQUENCY)));
			result.setCreatedAt(rs.getTimestamp(CourseHistory.getColumnName(CourseHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;	
	}

	
	
}