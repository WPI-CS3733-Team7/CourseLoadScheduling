package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSection;

public class CourseSectionsExtractor extends Extractor<List<CourseSection>>{

	@Override
	public List<CourseSection> extractData(ResultSet rs) throws SQLException
	{
		List<CourseSection> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseSection result = new CourseSection();
				
			result.setId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setSectionName(rs.getString(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME)));
			
			result.setSectionId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.SECTION_ID)));
			if(rs.wasNull()){
				result.setSectionId(null);
			}
			
			result.setSectionType(rs.getString(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE)));
			
			result.setPopulation(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.POPULATION)));
			if(rs.wasNull())
			{
				result.setPopulation(null);
			}
			
			result.setCourseId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID)));
			if(rs.wasNull())
			{
				result.setCourseId(null);
			}
			
			result.setInstructorId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID)));
			if(rs.wasNull())
			{
				result.setInstructorId(null);
			}
			
			result.setCalendarInfoId(rs.getInt(CourseSection.getColumnName(CourseSection.Columns.CALENDAR_INFO_ID)));
			if(rs.wasNull())
			{
				result.setCalendarInfoId(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT)));
			
			result.setDeleted(rs.getBoolean(CourseSection.getColumnName(CourseSection.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;		
	}

}
