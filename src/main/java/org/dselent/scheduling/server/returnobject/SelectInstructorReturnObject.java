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
	
	
}
