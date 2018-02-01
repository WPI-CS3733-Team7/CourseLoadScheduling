package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CourseSectionHistory;

public class CourseSectionsHistoryExtractor extends Extractor<List<CourseSectionHistory>>{

	@Override
	public List<CourseSectionHistory> extractData(ResultSet rs) throws SQLException
	{
		List<CourseSectionHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			CourseSectionHistory result = new CourseSectionHistory();
				
			result.setId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.FORMER_ID)));
			if(rs.wasNull())
			{
				result.setFormerId(null);
			}
			
			result.setSectionName(rs.getString(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_NAME)));
			
			result.setSectionId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_ID)));
			if(rs.wasNull())
			{
				result.setSectionId(null);
			}
			
			result.setSectionType(rs.getString(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.SECTION_TYPE)));
			
			result.setPopulation(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.POPULATION)));
			if(rs.wasNull())
			{
				result.setPopulation(null);
			}
			
			result.setCourseId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.COURSE_ID)));
			if(rs.wasNull())
			{
				result.setCourseId(null);
			}
			
			result.setInstructorId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.INSTRUCTOR_ID)));
			if(rs.wasNull())
			{
				result.setInstructorId(null);
			}
			
			result.setCalendarInfoId(rs.getInt(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CALENDAR_INFO_ID)));
			if(rs.wasNull())
			{
				result.setCalendarInfoId(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CourseSectionHistory.getColumnName(CourseSectionHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;		
	}

}