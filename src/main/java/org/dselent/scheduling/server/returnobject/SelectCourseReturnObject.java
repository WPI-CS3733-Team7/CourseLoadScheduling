package org.dselent.scheduling.server.returnobject;

import java.util.List;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;

public class SelectCourseReturnObject {
	private List<Instructor> instructorList;
	private List<CourseSection> courseSectionList;
	private List<CalendarInfo> calendarInfo;
	
	public SelectCourseReturnObject(List<Instructor> instructorList, List<CourseSection> courseSectionList,
			List<CalendarInfo> calendarInfo) {
		
		this.instructorList = instructorList;
		this.courseSectionList = courseSectionList;
		this.calendarInfo = calendarInfo;
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

	public List<CalendarInfo> getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(List<CalendarInfo> calendarInfo) {
		this.calendarInfo = calendarInfo;
	}
	
	

}
