package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CourseLoad extends Model
{
	// table name
	public static final String TABLE_NAME = "course_loads";
		
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
	public String toString() {
		return "CourseLoad [id=" + id + ", loadType=" + loadType + ", loadDescription=" + loadDescription
				+ ", instructorId=" + instructorId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", deleted=" + deleted + "]";
	}
	
}