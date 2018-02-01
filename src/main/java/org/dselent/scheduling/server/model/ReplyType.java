package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplyType extends Model
{
	// table name
	public static final String TABLE_NAME = "reply_types";
		
	// column names
	public static enum Columns
	{
		ID,
		REPLY_TYPE,
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
		COLUMN_TYPE_MAP.put(Columns.REPLY_TYPE, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
	};
	
	// attributes
	
	private Integer id;
	private String reply_type;
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
	
	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReplyType() {
		return reply_type;
	}

	public void setReplyType(String reply_type) {
		this.reply_type = reply_type;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	// Hashcode and Equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reply_type == null) ? 0 : reply_type.hashCode());
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
		ReplyType other = (ReplyType) obj;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reply_type == null) {
			if (other.reply_type != null)
				return false;
		} else if (!reply_type.equals(other.reply_type))
			return false;
		return true;
	}

	// toString
	
	@Override
	public String toString() {
		return "ReplyType [id=" + id + ", reply_type=" + reply_type + ", deleted=" + deleted + "]";
	}
	
	
}

