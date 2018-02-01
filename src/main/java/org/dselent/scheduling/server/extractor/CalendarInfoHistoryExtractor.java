package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.CalendarInfoHistory;

public class CalendarInfoHistoryExtractor extends Extractor<List<CalendarInfoHistory>>{

	@Override
	public List<CalendarInfoHistory> extractData(ResultSet rs) throws SQLException {
		
		List<CalendarInfoHistory> resultList = new ArrayList<>();

		while(rs.next())
		{
			CalendarInfoHistory result = new CalendarInfoHistory();
				
			result.setId(rs.getInt(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setFormerId(rs.getInt(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.FORMER_ID)));
			if(rs.wasNull())
			{
				result.setFormerId(null);
			}
			
			result.setCalYear(rs.getInt(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_YEAR)));
			if(rs.wasNull())
			{
				result.setCalYear(null);
			}
			
			result.setCalTerm(rs.getString(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CAL_TERM)));
			result.setDays(rs.getString(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.DAYS)));
			
			result.setStartTime(rs.getInt(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.START_TIME)));
			if(rs.wasNull())
			{
				result.setStartTime(null);
			}
			
			result.setEndTime(rs.getInt(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.END_TIME)));
			if(rs.wasNull())
			{
				result.setEndTime(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(CalendarInfoHistory.getColumnName(CalendarInfoHistory.Columns.CREATED_AT)));
		
			resultList.add(result);
		}
			
		return resultList;
	}

}
