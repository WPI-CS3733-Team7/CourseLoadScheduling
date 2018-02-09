package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;

public class SelectCourseReturnObject {

	private List<Instructor> instructorList;
	private List<CourseSection> courseSectionList;
	private List<CalendarInfo> calendarInfoList;
	
	public SelectCourseReturnObject(List<Instructor> instructorList, List<CourseSection> courseSectionList,
			List<CalendarInfo> calendarInfoList) {
		
		this.instructorList = instructorList;
		this.courseSectionList = courseSectionList;
		this.calendarInfoList = calendarInfoList;
	}
	
	@Override
	public String toString() {
		return "SelectInstructorReturnObject [instructorList=" + instructorList + ",\n courseSectionList=" + courseSectionList + ",\n calendarInfoList=" + calendarInfoList
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instructorList == null) ? 0 : instructorList.hashCode());
		result = prime * result + ((courseSectionList == null) ? 0 : courseSectionList.hashCode());
		result = prime * result + ((calendarInfoList == null) ? 0 : calendarInfoList.hashCode());
		
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
		SelectCourseReturnObject other = (SelectCourseReturnObject) obj;
		if(instructorList == null) {
			if (other.instructorList != null)
				return false;
		}else if (calendarInfoList == null) {
			if (other.calendarInfoList != null)
				return false;
		} else if (!calendarInfoList.equals(other.calendarInfoList))
			return false;
		if (courseSectionList == null) {
			if (other.courseSectionList != null)
				return false;
		} else if (!courseSectionList.equals(other.courseSectionList))
			return false;
		return true;
	}

	public List<Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(List<Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public List<CourseSection> getCourseSectionList() {
		return courseSectionList;
	}

	public void setCourseSectionList(List<CourseSection> courseSectionList) {
		this.courseSectionList = courseSectionList;
	}

	public List<CalendarInfo> getCalendarInfoList() {
		return calendarInfoList;
	}

	public void setCalendarInfo(List<CalendarInfo> calendarInfoList) {
		this.calendarInfoList = calendarInfoList;
	}
	
	
	
}

	
	


