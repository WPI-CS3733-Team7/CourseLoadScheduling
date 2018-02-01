package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course extends Model {
    // table name
    public static final String TABLE_NAME = "courses";

    // column names
    public static enum Columns {
        ID,
        COURSE_NAME,
        COURSE_NUMBER,
        FREQUENCY,
        CREATED_AT,
        UPDATED_AT,
        DELETED
    }

    // enum list
    private static final List<Course.Columns> COLUMN_LIST = new ArrayList<>();

    // type mapping
    private static final Map<Course.Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();

    static {
        for (Course.Columns key : Course.Columns.values()) {
            COLUMN_LIST.add(key);
        }

        COLUMN_TYPE_MAP.put(Course.Columns.ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(Course.Columns.COURSE_NAME, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Course.Columns.COURSE_NUMBER, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Course.Columns.FREQUENCY, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(Course.Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Course.Columns.UPDATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
        COLUMN_TYPE_MAP.put(Course.Columns.DELETED, JDBCType.BOOLEAN);
    }

    ;

    // attributes

    private Integer id;
    private String course_name;
    private String course_number;
    private String frequency;
    private Instant createdAt;
    private Instant updatedAt;
    private Boolean deleted;

    // methods

    public static JDBCType getColumnType(Course.Columns column) {
        return COLUMN_TYPE_MAP.get(column);
    }

    public static String getColumnName(Course.Columns column) {
        return column.toString().toLowerCase();
    }

    public static List<String> getColumnNameList() {
        List<String> columnNameList = new ArrayList<>();

        for (Course.Columns column : COLUMN_LIST) {
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

    public String getCourseName() {
        return course_name;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public String getCourseNumber() {
        return course_number;
    }

    public void setCourseNumber(String course_number) {
        this.course_number = course_number;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        if (createdAt != null) {
            this.createdAt = createdAt.toInstant();
        }
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        if (updatedAt != null) {
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
        result = prime * result + ((course_number == null) ? 0 : course_number.hashCode());
        result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
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
        if (!(obj instanceof Course))
        {
            return false;
        }
        Course other = (Course) obj;
        if (course_name == null)
        {
            if (other.course_name != null)
            {
                return false;
            }
        }
        else if (!course_name.equals(other.course_name))
        {
            return false;
        }
        if (course_number == null)
        {
            if (other.course_number != null)
            {
                return false;
            }
        }
        else if (!course_number.equals(other.course_number))
        {
            return false;
        }
        if (frequency == null)
        {
            if (other.course_number != null)
            {
                return false;
            }
        }
        else if (!course_number.equals(other.course_number))
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
        builder.append("Courses [id=");
        builder.append(id);
        builder.append(", courseName=");
        builder.append(course_name);
        builder.append(", courseNumber=");
        builder.append(course_number);
        builder.append(", Frequency=");
        builder.append(frequency);
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
