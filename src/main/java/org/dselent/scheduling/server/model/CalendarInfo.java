package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarInfo extends Model
{
	// table name
	public static final String TABLE_NAME = "calendar_info";
		
	// column names
	public static enum Columns
	{
		ID,
		CAL_YEAR,
		CAL_TERM,
		DAYS,
		START_TIME,
		END_TIME,
		CREATED_AT,
		UPDATED_AT,
		DELETED
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
		COLUMN_TYPE_MAP.put(Columns.CAL_YEAR, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CAL_TERM, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.DAYS, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.START_TIME, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.END_TIME, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
	};
	
	// attributes
	
	private Integer id;
	private Integer calYear;
	private String calTerm;
	private String days;
	private Integer startTime;
	private Integer endTime;
	private Instant createdAt;
	private Instant updatedAt;
	private Boolean deleted;
	
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

	//
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCalYear() {
		return calYear;
	}

	public void setCalYear(Integer cal_year) {
		this.calYear = cal_year;
	}

	public String getCalTerm() {
		return calTerm;
	}

	public void setCalTerm(String cal_term) {
		this.calTerm = cal_term;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer start_time) {
		this.startTime = start_time;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer end_time) {
		this.endTime = end_time;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		if (updatedAt != null)
			this.createdAt = createdAt.toInstant();
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		if (updatedAt != null)
			this.updatedAt = updatedAt.toInstant();
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calTerm == null) ? 0 : calTerm.hashCode());
		result = prime * result + ((calYear == null) ? 0 : calYear.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		CalendarInfo other = (CalendarInfo) obj;
		if (calTerm == null) {
			if (other.calTerm != null)
				return false;
		} else if (!calTerm.equals(other.calTerm))
			return false;
		if (calYear == null) {
			if (other.calYear != null)
				return false;
		} else if (!calYear.equals(other.calYear))
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
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalendarInfo [id=" + id + ", cal_year=" + calYear + ", cal_term=" + calTerm + ", days=" + days
				+ ", start_time=" + startTime + ", end_time=" + endTime + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", deleted=" + deleted + "]";
	}
	
}