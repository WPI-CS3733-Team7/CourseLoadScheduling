package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarInfoHistory extends Model
{
	// table name
	public static final String TABLE_NAME = "calendar_info_history";
		
	// column names
	public static enum Columns
	{
		ID,
		FORMER_ID,
		CAL_YEAR,
		CAL_TERM,
		DAYS,
		START_TIME,
		END_TIME,
		CREATED_AT
	}
	
	// enum list
	private static final List<Columns> COLUMN_LIST = new ArrayList<>();
	
	// type mapping
	private static final Map<Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();
	
	static
	{
		for(Columns key : Columns.values())
		{
			COLUMN_LIST.add(key);
		}
		
		COLUMN_TYPE_MAP.put(Columns.ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.FORMER_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CAL_YEAR, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CAL_TERM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.DAYS, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.START_TIME, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.END_TIME, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};
	
	// attributes
	
	private Integer id;
	private Integer former_id;
	private Integer cal_year;
	private String cal_term;
	private String days;
	private Integer start_time;
	private Integer end_time;
	private Instant createdAt;
	
	// methods
		
	public static JDBCType getColumnType(Columns column)
	{
		return COLUMN_TYPE_MAP.get(column);
	}
	
	public static String getColumnName(Columns column)
	{
		return column.toString().toLowerCase();
	}
	
	public static List<String> getColumnNameList()
	{
		List<String> columnNameList = new ArrayList<>();
		
		for(Columns column : COLUMN_LIST)
		{
			columnNameList.add(getColumnName(column));
		}
		
		return columnNameList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFormerId() {
		return former_id;
	}

	public void setFormerId(Integer former_id) {
		this.former_id = former_id;
	}

	public Integer getCalYear() {
		return cal_year;
	}

	public void setCalYear(Integer cal_year) {
		this.cal_year = cal_year;
	}

	public String getCalTerm() {
		return cal_term;
	}

	public void setCalTerm(String cal_term) {
		this.cal_term = cal_term;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Integer getStartTime() {
		return start_time;
	}

	public void setStartTime(Integer start_time) {
		this.start_time = start_time;
	}

	public Integer getEndTime() {
		return end_time;
	}

	public void setEndTime(Integer end_time) {
		this.end_time = end_time;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		if (createdAt != null)
			this.createdAt = createdAt.toInstant();
	}

	//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cal_term == null) ? 0 : cal_term.hashCode());
		result = prime * result + ((cal_year == null) ? 0 : cal_year.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((end_time == null) ? 0 : end_time.hashCode());
		result = prime * result + ((former_id == null) ? 0 : former_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((start_time == null) ? 0 : start_time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendarInfoHistory other = (CalendarInfoHistory) obj;
		if (cal_term == null) {
			if (other.cal_term != null)
				return false;
		} else if (!cal_term.equals(other.cal_term))
			return false;
		if (cal_year == null) {
			if (other.cal_year != null)
				return false;
		} else if (!cal_year.equals(other.cal_year))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (end_time == null) {
			if (other.end_time != null)
				return false;
		} else if (!end_time.equals(other.end_time))
			return false;
		if (former_id == null) {
			if (other.former_id != null)
				return false;
		} else if (!former_id.equals(other.former_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (start_time == null) {
			if (other.start_time != null)
				return false;
		} else if (!start_time.equals(other.start_time))
			return false;
		return true;
	}

	//
	
	@Override
	public String toString() {
		return "CalendarInfoHistory [id=" + id + ", former_id=" + former_id + ", cal_year=" + cal_year + ", cal_term="
				+ cal_term + ", days=" + days + ", start_time=" + start_time + ", end_time=" + end_time + ", createdAt="
				+ createdAt + "]";
	}	
	
}
