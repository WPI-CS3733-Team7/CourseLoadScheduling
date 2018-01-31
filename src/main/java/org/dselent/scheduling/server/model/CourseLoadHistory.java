package org.dselent.scheduling.server.model;

import java.sql.JDBCType;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CourseLoadHistory extends Model
{
	// table name
	public static final String TABLE_NAME = "course_loads_history";
		
	// column names
	public static enum Columns
	{
		ID,
		FORMER_ID,
		LOAD_TYPE,
		LOAD_DESCRIPTION,
		INSTRUCTOR_ID,
		CREATED_AT,
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
		COLUMN_TYPE_MAP.put(Columns.LOAD_TYPE, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.LOAD_DESCRIPTION, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.INSTRUCTOR_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
	};
	
	// attributes
	
	private Integer id;
	private Integer formerId;
	private String loadType;
	private String loadDescription;
	private Integer instructorId;
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
	
	//
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFormerId() {
		return formerId;
	}
	
	public void setFormerId(Integer formerId) {
		this.formerId = formerId;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	public String getLoadDescription() {
		return loadDescription;
	}

	public void setLoadDescription(String loadDescription) {
		this.loadDescription = loadDescription;
	}

	public Integer getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Integer instructorId) {
		this.instructorId = instructorId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		if (createdAt != null)
		this.createdAt = createdAt.toInstant();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((formerId == null) ? 0 : formerId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((loadDescription == null) ? 0 : loadDescription.hashCode());
		result = prime * result + ((loadType == null) ? 0 : loadType.hashCode());
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
		CourseLoadHistory other = (CourseLoadHistory) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (formerId == null) {
			if (other.formerId != null)
				return false;
		} else if (!formerId.equals(other.formerId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (loadDescription == null) {
			if (other.loadDescription != null)
				return false;
		} else if (!loadDescription.equals(other.loadDescription))
			return false;
		if (loadType == null) {
			if (other.loadType != null)
				return false;
		} else if (!loadType.equals(other.loadType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseLoadHistory [id=" + id + ", formerId=" + formerId + ", loadType=" + loadType
				+ ", loadDescription=" + loadDescription + ", instructorId=" + instructorId + ", createdAt=" + createdAt
				+ "]";
	}	
}