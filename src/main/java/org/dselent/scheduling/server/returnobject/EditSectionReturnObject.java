package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;

public class EditSectionReturnObject {
	
	private String message;
	private CourseSection courseSection;
	private CalendarInfo calendarInfo;
	
	public EditSectionReturnObject(String message, CourseSection courseSection, CalendarInfo calendarInfo) {
		super();
		this.message = message;
		this.courseSection = courseSection;
		this.calendarInfo = calendarInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CourseSection getCourseSection() {
		return courseSection;
	}

	public void setCourseSection(CourseSection courseSection) {
		this.courseSection = courseSection;
	}

	public CalendarInfo getCalendarInfo() {
		return calendarInfo;
	}

	public void setCalendarInfo(CalendarInfo calendarInfo) {
		this.calendarInfo = calendarInfo;
	}
}
