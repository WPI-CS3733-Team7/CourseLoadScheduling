package org.dselent.scheduling.server.model;

import java.sql.JDBCType;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSectionHistory extends Model {
	// table name
		public static final String TABLE_NAME = "course_sections_history";
			
		// column names
		public static enum Columns
		{
			ID,
			FORMER_ID,
			SECTION_NAME,
			SECTION_ID,
			SECTION_TYPE,
			POPULATION,
			COURSE_ID,
			INSTRUCTOR_ID,
			CALENDAR_INFO_ID,
			CREATED_AT
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
			COLUMN_TYPE_MAP.put(Columns.SECTION_NAME, JDBCType.VARCHAR);
			COLUMN_TYPE_MAP.put(Columns.SECTION_ID, JDBCType.INTEGER);
			COLUMN_TYPE_MAP.put(Columns.SECTION_TYPE, JDBCType.VARCHAR);
			COLUMN_TYPE_MAP.put(Columns.POPULATION, JDBCType.INTEGER);
			COLUMN_TYPE_MAP.put(Columns.COURSE_ID, JDBCType.INTEGER);
			COLUMN_TYPE_MAP.put(Columns.INSTRUCTOR_ID, JDBCType.INTEGER);
			COLUMN_TYPE_MAP.put(Columns.CALENDAR_INFO_ID, JDBCType.INTEGER);
			COLUMN_TYPE_MAP.put(Columns.CREATED_AT, JDBCType.TIMESTAMP_WITH_TIMEZONE);
		};
		
		// attributes
		
		private Integer id;
		private Integer formerId;
		private String sectionName;
		private Integer sectionId;
		private String sectionType;
		private Integer population;
		private Integer courseId;
		private Integer instructorId;
		private Integer calendarInfoId;
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
		
		public static List<String> getColumnNameList() {
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

		public Integer getFormerId() {
			return formerId;
		}

		public void setFormerId(Integer formerId) {
			this.formerId = formerId;
		}

		public String getSectionName() {
			return sectionName;
		}

		public void setSectionName(String sectionName) {
			this.sectionName = sectionName;
		}

		public Integer getSectionId() {
			return sectionId;
		}

		public void setSectionId(Integer sectionId) {
			this.sectionId = sectionId;
		}

		public String getSectionType() {
			return sectionType;
		}

		public void setSectionType(String sectionType) {
			this.sectionType = sectionType;
		}

		public Integer getPopulation() {
			return population;
		}

		public void setPopulation(Integer population) {
			this.population = population;
		}

		public Integer getCourseId() {
			return courseId;
		}

		public void setCourseId(Integer courseId) {
			this.courseId = courseId;
		}

		public Integer getInstructorId() {
			return instructorId;
		}

		public void setInstructorId(Integer instructorId) {
			this.instructorId = instructorId;
		}

		public Integer getCalendarInfoId() {
			return calendarInfoId;
		}

		public void setCalendarInfoId(Integer calendarInfoId) {
			this.calendarInfoId = calendarInfoId;
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
			result = prime * result + ((calendarInfoId == null) ? 0 : calendarInfoId.hashCode());
			result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
			result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
			result = prime * result + ((formerId == null) ? 0 : formerId.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
			result = prime * result + ((population == null) ? 0 : population.hashCode());
			result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
			result = prime * result + ((sectionName == null) ? 0 : sectionName.hashCode());
			result = prime * result + ((sectionType == null) ? 0 : sectionType.hashCode());
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
			CourseSectionHistory other = (CourseSectionHistory) obj;
			if (calendarInfoId == null) {
				if (other.calendarInfoId != null)
					return false;
			} else if (!calendarInfoId.equals(other.calendarInfoId))
				return false;
			if (courseId == null) {
				if (other.courseId != null)
					return false;
			} else if (!courseId.equals(other.courseId))
				return false;
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
			if (population == null) {
				if (other.population != null)
					return false;
			} else if (!population.equals(other.population))
				return false;
			if (sectionId == null) {
				if (other.sectionId != null)
					return false;
			} else if (!sectionId.equals(other.sectionId))
				return false;
			if (sectionName == null) {
				if (other.sectionName != null)
					return false;
			} else if (!sectionName.equals(other.sectionName))
				return false;
			if (sectionType == null) {
				if (other.sectionType != null)
					return false;
			} else if (!sectionType.equals(other.sectionType))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CourseSectionHistory [id=" + id + ", formerId=" + formerId + ", sectionName=" + sectionName
					+ ", sectionId=" + sectionId + ", sectionType=" + sectionType + ", population=" + population
					+ ", courseId=" + courseId + ", instructorId=" + instructorId + ", calendarInfoId=" + calendarInfoId
					+ ", createdAt=" + createdAt + "]";
		}
}
