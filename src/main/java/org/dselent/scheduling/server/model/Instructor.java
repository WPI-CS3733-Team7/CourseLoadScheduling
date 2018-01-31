package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Instructor extends Model {
    // table name
    public static final String TABLE_NAME = "instructors";

    // column names
    public static enum Columns
    {
        ID,
        RANK,
        FIRST_NAME,
        LAST_NAME,
        EMAIL,
        CREATED_AT,
        UPDATED_AT,
        DELETED
    }

    // enum list
    private static final List<Instructor.Columns> COLUMN_LIST = new ArrayList<>();

    // type mapping
    private static final Map<Instructor.Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();

    static
    {
        for(Instructor.Columns key : Instructor.Columns.values())
        {
            COLUMN_LIST.add(key);
        }

        COLUMN_TYPE_MAP.put(Instructor.Columns.ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Instructor.Columns.RANK, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Instructor.Columns.FIRST_NAME, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Instructor.Columns.LAST_NAME, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Instructor.Columns.EMAIL, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Instructor.Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Instructor.Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Instructor.Columns.DELETED, JDBCType.BOOLEAN);
    };

    // attributes

    private Integer id;
    private String rank;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean deleted;

    // methods

    public static JDBCType getColumnType(Instructor.Columns column)
    {
        return COLUMN_TYPE_MAP.get(column);
    }

    public static String getColumnName(Instructor.Columns column)
    {
        return column.toString().toLowerCase();
    }

    public static List<String> getColumnNameList()
    {
        List<String> columnNameList = new ArrayList<>();

        for(Instructor.Columns column : COLUMN_LIST)
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

    public String getRank()
    {
        return rank;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
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
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
        if (!(obj instanceof Instructor))
        {
            return false;
        }
        Instructor other = (Instructor) obj;
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
        if (email == null)
        {
            if (other.email != null)
            {
                return false;
            }
        }
        else if (!email.equals(other.email))
        {
            return false;
        }
        if (firstName == null)
        {
            if (other.firstName != null)
            {
                return false;
            }
        }
        else if (!firstName.equals(other.firstName))
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
        if (lastName == null)
        {
            if (other.lastName != null)
            {
                return false;
            }
        }
        else if (!lastName.equals(other.lastName))
        {
            return false;
        }
        if (rank == null)
        {
            if (other.rank != null)
            {
                return false;
            }
        }
        else if (!rank.equals(other.rank))
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
        builder.append(", Rank=");
        builder.append(rank);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
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