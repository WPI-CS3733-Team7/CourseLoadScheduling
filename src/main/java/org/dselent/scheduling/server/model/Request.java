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
        REQUEST_TYPE,
        REQUEST_DETAILS,
        REPLY,
        REPLY_TYPE,
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
        COLUMN_TYPE_MAP.put(Request.Columns.REQUEST_TYPE, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Request.Columns.REQUEST_DETAILS, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Request.Columns.REPLY, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Request.Columns.REPLY_TYPE, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Request.Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Request.Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Request.Columns.DELETED, JDBCType.BOOLEAN);
    };

    // attributes

    private Integer id;
    private Integer requester_id;
    private String request_type;
    private String request_details;
    private String reply;
    private String reply_type;
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

    public Integer getRequesterId()
    {
        return requester_id;
    }

    public void setRequesterId(Integer requester_id)
    {
        this.requester_id = requester_id;
    }

    public String getRequestType()
    {
        return request_type;
    }

    public void setRequestType(String request_type)
    {
        this.request_type = request_type;
    }

    public String getRequestDetails()
    {
        return request_details;
    }

    public void setRequestDetails(String request_details)
    {
        this.request_details = request_details;
    }
    public String getReply()
    {
    		return reply;
    }
    public void setReply(String reply)
    {
    		this.reply = reply;
    }
    public String getReplyType()
    {
        return reply_type;
    }

    public void setReplyType(String reply_type)
    {
        this.reply_type = reply_type;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
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
        result = prime * result + ((request_type == null) ? 0 : request_type.hashCode());
        result = prime * result + ((reply == null) ? 0 : reply.hashCode());
        result = prime * result + ((reply_type == null) ? 0 : reply_type.hashCode());
        result = prime * result + ((request_details == null) ? 0 : request_details.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((requester_id == null) ? 0 : requester_id.hashCode());
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
        if (requester_id == null)
        {
            if (other.requester_id != null)
            {
                return false;
            }
        }
        else if (!requester_id.equals(other.requester_id))
        {
            return false;
        }
        if (request_details == null)
        {
            if (other.request_details != null)
            {
                return false;
            }
        }
        else if (!request_details.equals(other.request_details))
        {
            return false;
        }
        if (reply_type == null)
        {
            if (other.reply_type != null)
            {
                return false;
            }
        }
        else if (!reply_type.equals(other.reply_type))
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
        builder.append(requester_id);
        builder.append(", RequestType=");
        builder.append(request_type);
        builder.append(", RequestDetails=");
        builder.append(request_details);
        builder.append(", Reply=");
        builder.append(reply);
        builder.append(", ReplyType=");
        builder.append(reply_type);
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

