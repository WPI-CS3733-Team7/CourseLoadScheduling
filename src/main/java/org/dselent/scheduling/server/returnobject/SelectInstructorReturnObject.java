package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;

public class SelectInstructorReturnObject {
	List<CourseSection> sectionList;
	List<CalendarInfo> calendarInfoList;
	
	public SelectInstructorReturnObject(List<CourseSection> sectionList, List<CalendarInfo> calendarInfoList) {
		this.sectionList = sectionList;
		this.calendarInfoList = calendarInfoList;
	}

	@Override
	public String toString() {
		return "SelectInstructorReturnObject [sectionList=" + sectionList + ",\n calendarInfoList=" + calendarInfoList
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calendarInfoList == null) ? 0 : calendarInfoList.hashCode());
		result = prime * result + ((sectionList == null) ? 0 : sectionList.hashCode());
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
		SelectInstructorReturnObject other = (SelectInstructorReturnObject) obj;
		if (calendarInfoList == null) {
			if (other.calendarInfoList != null)
				return false;
		} else if (!calendarInfoList.equals(other.calendarInfoList))
			return false;
		if (sectionList == null) {
			if (other.sectionList != null)
				return false;
		} else if (!sectionList.equals(other.sectionList))
			return false;
		return true;
	}
	
	
	
}
