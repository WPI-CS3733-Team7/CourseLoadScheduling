package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request extends Model{
    // table name
    public static final String TABLE_NAME = "Requests";

    // column names
    public static enum Columns
    {
        ID,
        REQUESTER_ID,
        REQUEST_TYPE_ID,
        REQUEST_DETAILS,
        REPLY_TYPE_ID,
        CREATED_AT,
        UPDATED_AT,
        DELETED
    }

    // enum list
    private static final List<Request.Columns> COLUMN_LIST = new ArrayList<>();

    // type mapping
    private static final Map<Request.Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();

    static
    {
        for(Request.Columns key : Request.Columns.values())
        {
            COLUMN_LIST.add(key);
        }

        COLUMN_TYPE_MAP.put(Request.Columns.ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Request.Columns.REQUESTER_ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Request.Columns.REQUEST_TYPE_ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Request.Columns.REQUEST_DETAILS, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Request.Columns.REPLY_TYPE_ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Request.Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Request.Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Request.Columns.DELETED, JDBCType.BOOLEAN);
    };

    // attributes

    private Integer id;
    private String requester_ID;
    private String request_type_ID;
    private String request_Details;
    private String reply_type_ID;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean deleted;

    // methods

    public static JDBCType getColumnType(Request.Columns column)
    {
        return COLUMN_TYPE_MAP.get(column);
    }

    public static String getColumnName(Request.Columns column)
    {
        return column.toString().toLowerCase();
    }

    public static List<String> getColumnNameList()
    {
        List<String> columnNameList = new ArrayList<>();

        for(Request.Columns column : COLUMN_LIST)
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

    public String getRequester_ID()
    {
        return requester_ID;
    }

    public void setRequester_ID(String requester_ID)
    {
        this.requester_ID = requester_ID;
    }

    public String getRequest_type_ID()
    {
        return request_type_ID;
    }

    public void setRequest_type_ID(String request_type_ID)
    {
        this.request_type_ID = request_type_ID;
    }

    public String getRequest_Details()
    {
        return request_Details;
    }

    public void setRequest_Details(String request_Details)
    {
        this.request_Details = request_Details;
    }

    public String getReply_type_ID()
    {
        return reply_type_ID;
    }

    public void setReply_type_ID(String reply_type_ID)
    {
        this.reply_type_ID = reply_type_ID;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((request_type_ID == null) ? 0 : request_type_ID.hashCode());
        result = prime * result + ((reply_type_ID == null) ? 0 : reply_type_ID.hashCode());
        result = prime * result + ((request_Details == null) ? 0 : request_Details.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((requester_ID == null) ? 0 : requester_ID.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
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
        if (!(obj instanceof Request))
        {
            return false;
        }
        Request other = (Request) obj;
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
        if (requester_ID == null)
        {
            if (other.requester_ID != null)
            {
                return false;
            }
        }
        else if (!requester_ID.equals(other.requester_ID))
        {
            return false;
        }
        if (request_Details == null)
        {
            if (other.request_Details != null)
            {
                return false;
            }
        }
        else if (!request_Details.equals(other.request_Details))
        {
            return false;
        }
        if (reply_type_ID == null)
        {
            if (other.reply_type_ID != null)
            {
                return false;
            }
        }
        else if (!reply_type_ID.equals(other.reply_type_ID))
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
        if (deleted == null) {
            if (other.deleted != null)
                return false;
        }
        else if (!deleted.equals(other.deleted))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Users [id=");
        builder.append(id);
        builder.append(", RequesterID=");
        builder.append(requester_ID);
        builder.append(", RequestTypeID=");
        builder.append(request_type_ID);
        builder.append(", RequestDetails=");
        builder.append(request_Details);
        builder.append(", ReplyTypeID=");
        builder.append(reply_type_ID);
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

