package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRole extends Model
{
	// table name
	public static final String TABLE_NAME = "user_roles";
		
	// column names
	public static enum Columns
	{
		ID,
		ROLE_NAME,
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
		COLUMN_TYPE_MAP.put(Columns.ROLE_NAME, JDBCType.VARCHAR);
		COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		COLUMN_TYPE_MAP.put(Columns.DELETED, JDBCType.BOOLEAN);
	};
	
	// attributes
	
	private Integer id;
	private String roleName;
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
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Instant getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt)
	{
		this.createdAt = createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt)
	{
		if(createdAt != null)
		{
			this.createdAt = createdAt.toInstant();
		}
	}

	public Instant getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt)
	{
		this.updatedAt = updatedAt;
	}
	
	public void setUpdatedAt(Timestamp updatedAt)
	{
		if(updatedAt != null)
		{
			this.updatedAt = updatedAt.toInstant();
		}
	}
	
	public Boolean isDeleted()
	{
		return deleted;
	}
	
	public void setDeleted(Boolean deleted)
	{
		this.deleted = deleted;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof UserRole))
		{
			return false;
		}
		UserRole other = (UserRole) obj;
		if (createdAt == null)
		{
			if (other.createdAt != null)
			{
				return false;
			}
		}
		else if (!createdAt.equals(other.createdAt))
		{
			return false;
		}
		if (deleted == null)
		{
			if (other.deleted != null)
			{
				return false;
			}
		}
		else if (!deleted.equals(other.deleted))
		{
			return false;
		}
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		if (roleName == null)
		{
			if (other.roleName != null)
			{
				return false;
			}
		}
		else if (!roleName.equals(other.roleName))
		{
			return false;
		}
		if (updatedAt == null)
		{
			if (other.updatedAt != null)
			{
				return false;
			}
		}
		else if (!updatedAt.equals(other.updatedAt))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserRoles [id=");
		builder.append(id);
		builder.append(", roleName=");
		builder.append(roleName);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append("]");
		return builder.toString();
	}
}
