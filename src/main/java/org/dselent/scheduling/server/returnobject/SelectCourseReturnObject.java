package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;

public class SelectCourseReturnObject {
	List<Instructor> instructorList;
	List<CourseSection> courseSectionList;
	List<CalendarInfo> calendarInfo;
	
	public SelectCourseReturnObject(List<Instructor> instructorList, List<CourseSection> courseSectionList,
			List<CalendarInfo> calendarInfo) {
		
		this.instructorList = instructorList;
		this.courseSectionList = courseSectionList;
		this.calendarInfo = calendarInfo;
	}
	
	

}
