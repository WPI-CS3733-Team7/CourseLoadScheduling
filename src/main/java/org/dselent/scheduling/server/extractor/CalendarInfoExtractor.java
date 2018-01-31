package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CalendarInfo;

public class CalendarInfoExtractor extends Extractor<List<CalendarInfo>>{

	@Override
	public List<CalendarInfo> extractData(ResultSet rs) throws SQLException {

		List<CalendarInfo> resultList = new ArrayList<>();

		while(rs.next())
		{
			CalendarInfo result = new CalendarInfo();
				
			result.setId(rs.getInt(CalendarInfo.getColumnName(CalendarInfo.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setCalYear(rs.getInt(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR)));
			if(rs.wasNull())
			{
				result.setCalYear(null);
			}
			
			result.setCalTerm(rs.getString(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM)));
			result.setDays(rs.getString(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS)));
			
			result.setStartTime(rs.getInt(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME)));
			if(rs.wasNull())
			{
				result.setStartTime(null);
			}
			
			result.setEndTime(rs.getInt(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME)));
			if(rs.wasNull())
			{
				result.setEndTime(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CalendarInfo.getColumnName(CalendarInfo.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(CalendarInfo.getColumnName(CalendarInfo.Columns.UPDATED_AT)));
			
			result.setDeleted(rs.getBoolean(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;	
	}

}
