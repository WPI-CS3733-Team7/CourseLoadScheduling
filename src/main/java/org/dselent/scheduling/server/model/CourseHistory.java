package org.dselent.scheduling.server.model;

import java.sql.JDBCType;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseHistory extends Model{
    public static final String TABLE_NAME = "CoursesHistory";

    // column names
    public static enum Columns {
        ID,
        FORMER_ID,
        COURSE_NAME,
        COURSE_NUMBER,
        FREQUENCY,
        CREATED_AT
    }

    // enum list
    private static final List<CourseHistory.Columns> COLUMN_LIST = new ArrayList<>();

    // type mapping
    private static final Map<CourseHistory.Columns, JDBCType> COLUMN_TYPE_MAP = new HashMap<>();

    static {
        for (CourseHistory.Columns key : CourseHistory.Columns.values()) {
            COLUMN_LIST.add(key);
        }

        COLUMN_TYPE_MAP.put(CourseHistory.Columns.ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(CourseHistory.Columns.FORMER_ID, JDBCType.INTEGER);
        COLUMN_TYPE_MAP.put(CourseHistory.Columns.COURSE_NAME, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(CourseHistory.Columns.COURSE_NUMBER, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(CourseHistory.Columns.FREQUENCY, JDBCType.VARCHAR);
        COLUMN_TYPE_MAP.put(CourseHistory.Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
    }

    ;

    // attributes

    private Integer id;
    private Integer former_id;
    private String course_name;
    private String course_number;
    private String frequency;
    private Instant createdAt;

    // methods

    public static JDBCType getColumnType(CourseHistory.Columns column) {
        return COLUMN_TYPE_MAP.get(column);
    }

    public static String getColumnName(CourseHistory.Columns column) {
        return column.toString().toLowerCase();
    }

    public static List<String> getColumnNameList() {
        List<String> columnNameList = new ArrayList<>();

        for (CourseHistory.Columns column : COLUMN_LIST) {
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

    public Integer getFormer_id() {
        return former_id;
    }

    public void setFormer_id(Integer former_id) {
        this.former_id = former_id;
    }

    public String getCourseName() {
        return course_name;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((former_id == null) ? 0 : former_id.hashCode());
        result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
        result = prime * result + ((course_number == null) ? 0 : course_number.hashCode());
        result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
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
        if (!(obj instanceof CourseHistory))
        {
            return false;
        }
        CourseHistory other = (CourseHistory) obj;
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
        if (former_id == null)
        {
            if (other.former_id != null)
            {
                return false;
            }
        }
        else if (!former_id.equals(other.former_id))
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
        if (frequency == null)
        {
            if(other.frequency != null)
            {
                return false;
            }
        }
        else if (!former_id.equals(other.frequency))
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
        builder.append(". formerID=");
        builder.append(former_id);
        builder.append(", courseName=");
        builder.append(course_name);
        builder.append(", courseNumber=");
        builder.append(course_number);
        builder.append(", Frequency=");
        builder.append(frequency);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append("]");
        return builder.toString();
    }

}