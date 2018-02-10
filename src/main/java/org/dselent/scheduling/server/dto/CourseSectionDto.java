package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseSection;


public class CourseSectionDto
{
	private final CourseSection section;
	private final CalendarInfo cal;
	private final String instFirstName;
	private final String instLastName;
	private final String courseName;

	@Generated("SparkTools")
	private CourseSectionDto(Builder builder)
	{
		
		this.section = builder.section;
		this.cal = builder.cal;
		this.instFirstName = builder.instFirstName;
		this.instLastName = builder.instLastName;
		this.courseName = builder.courseName;
		
		
	}

	public CourseSection getSection() {
		return section;
	}


	public CalendarInfo getCal() {
		return cal;
	}


	public String getInstFirstName() {
		return instFirstName;
	}


	public String getInstLastName() {
		return instLastName;
	}


	public String getCourseName() {
		return courseName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cal == null) ? 0 : cal.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((instFirstName == null) ? 0 : instFirstName.hashCode());
		result = prime * result + ((instLastName == null) ? 0 : instLastName.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		CourseSectionDto other = (CourseSectionDto) obj;
		if (cal == null) {
			if (other.cal != null)
				return false;
		} else if (!cal.equals(other.cal))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (instFirstName == null) {
			if (other.instFirstName != null)
				return false;
		} else if (!instFirstName.equals(other.instFirstName))
			return false;
		if (instLastName == null) {
			if (other.instLastName != null)
				return false;
		} else if (!instLastName.equals(other.instLastName))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "CourseSectionDto [section=" + section + ", cal=" + cal + ", instFirstName=" + instFirstName
				+ ", instLastName=" + instLastName + ", courseName=" + courseName + "]";
	}

	/**
	 * Creates builder to build {@link CourseSectionDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 * Builder to build {@link CourseSectionDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder
	{
		private CourseSection section;
		private CalendarInfo cal;
		private String instFirstName;
		private String instLastName;
		private String courseName;

		private Builder()
		{
		}

		public Builder withSection(CourseSection section)
		{
			this.section = section;
			return this;
		}

		public Builder withCal(CalendarInfo cal)
		{
			this.cal = cal;
			return this;
		}

		public Builder withInstFirstName(String instFirstName)
		{
			this.instFirstName = instFirstName;
			return this;
		}

		public Builder withInstLastName(String instLastName)
		{
			this.instLastName = instLastName;
			return this;
		}

		public Builder withCourseName(String courseName)
		{
			this.courseName = courseName;
			return this;
		}

		public CourseSectionDto build()
		{
			return new CourseSectionDto(this);
		}
	}
}
