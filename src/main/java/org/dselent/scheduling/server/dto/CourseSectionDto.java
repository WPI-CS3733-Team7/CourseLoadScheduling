package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseSection;


public class CourseSectionDto
{
	private final CourseSection section;
	private final CalendarInfo cal;
	private final Integer courseId;
	private final Integer instructorId;

	@Generated("SparkTools")
	private CourseSectionDto(Builder builder)
	{
		this.section = builder.section;
		this.cal = builder.cal;
		this.courseId = builder.courseId;
		this.instructorId = builder.instructorId;
	}

	public CourseSection getSection() {
		return section;
	}


	public CalendarInfo getCal() {
		return cal;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public Integer getInstructorId() {
		return instructorId;
	}

	@Override
	public String toString() {
		return "CourseSectionDto [section=" + section + ", cal=" + cal + ", courseId=" + courseId + ", instructorId="
				+ instructorId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cal == null) ? 0 : cal.hashCode());
		result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
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
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
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
		private Integer courseId;
		private Integer instructorId;

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

		public Builder withInstructorId(Integer instructorId)
		{
			this.instructorId = instructorId;
			return this;
		}

		public Builder withCourseId(Integer courseId)
		{
			this.courseId = courseId;
			return this;
		}

		public CourseSectionDto build()
		{
			return new CourseSectionDto(this);
		}
	}
}
