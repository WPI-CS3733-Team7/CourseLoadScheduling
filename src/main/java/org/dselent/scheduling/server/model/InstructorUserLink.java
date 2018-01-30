package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InstructorUserLink extends Model
{
	// table name
	public static final String TABLE_NAME = "instructor_user_links";
		
	// column names
	public static enum Columns
	{
		ID,
		INSTRUCTOR_ID,
		LINKED_USER_ID,
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
		COLUMN_TYPE_MAP.put(Columns.INSTRUCTOR_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.LINKED_USER_ID, JDBCType.INTEGER);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
	};
	
	// attributes
	
	private Integer id;
	private Integer instructor_id;
	private Integer linked_user_id;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstructor_id() {
		return instructor_id;
	}

	public void setInstructor_id(Integer instructor_id) {
		this.instructor_id = instructor_id;
	}

	public Integer getLinked_user_id() {
		return linked_user_id;
	}

	public void setLinked_user_id(Integer linked_user_id) {
		this.linked_user_id = linked_user_id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		if (createdAt != null)
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
	
	
	
}