package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.Course;

public class CoursesExtractor extends Extractor<List<Course>>{

	@Override
	public List<Course> extractData(ResultSet rs) throws SQLException
	{
		List<Course> resultList = new ArrayList<>();

		while(rs.next())
		{
			Course result = new Course();
				
			result.setId(rs.getInt(Course.getColumnName(Course.Columns.ID)));
			
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setCourseName(rs.getString(Course.getColumnName(Course.Columns.COURSE_NAME)));
			result.setCourseNumber(rs.getString(Course.getColumnName(Course.Columns.COURSE_NUMBER)));
			result.setFrequency(rs.getString(Course.getColumnName(Course.Columns.FREQUENCY)));
			
			result.setCreatedAt(rs.getTimestamp(Course.getColumnName(Course.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(Course.getColumnName(Course.Columns.UPDATED_AT)));
			
			result.setDeleted(rs.getBoolean(Course.getColumnName(Course.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;	
	}

	
	
}
