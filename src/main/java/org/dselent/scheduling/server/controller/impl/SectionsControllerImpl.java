package org.dselent.scheduling.server.controller.impl;

import java.util.HashMap;
import java.util.Map;

import org.dselent.scheduling.server.controller.SectionsController;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.requests.SectionEdit;
import org.dselent.scheduling.server.returnobject.EditSectionReturnObject;
import org.dselent.scheduling.server.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SectionsControllerImpl implements SectionsController{
	
	@Autowired
    private SectionsService sectionsService;
	
	public ResponseEntity<String> edit(@RequestBody Map<String, String> request) throws Exception
	{
		// add any objects that need to be returned to the success list
		String response = "";
		System.out.println("Edit function reached");
		CourseSection newSection = new CourseSection();
		CalendarInfo newCal = new CalendarInfo();
		
		Integer id = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.ID)));
		Integer courseId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.COURSE_ID)));
		Integer instructorId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.INSTRUCTOR_ID)));
		Integer calendarInfoId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.CALENDAR_INFO_ID)));
		String sectionName = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NAME));
		Integer sectionId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_ID)));
		String sectionType = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_TYPE));
		Integer population = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.POPULATION)));
		Integer year = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.YEAR)));
		String term = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.TERM));
		String days = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DAYS));
		Integer startTime = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.START_TIME)));
		Integer endTime = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.END_TIME)));
		Boolean deleted = Boolean.parseBoolean(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DELETED)));
		
		newSection.setId(id);
		newSection.setCourseId(courseId);
		newSection.setInstructorId(instructorId);
		newSection.setCalendarInfoId(calendarInfoId);
		newSection.setSectionName(sectionName);
		newSection.setSectionId(sectionId);
		newSection.setSectionType(sectionType);
		newSection.setPopulation(population);
		newSection.setDeleted(deleted);
		
		newCal.setCalYear(year);
		newCal.setCalTerm(term);
		newCal.setDays(days);
		newCal.setStartTime(startTime);
		newCal.setEndTime(endTime);
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto courseSectionDto = builder.withSection(newSection)
		.withCal(newCal)
		.withInstructorId(instructorId)
		.withCourseId(courseId)
		.build();
		
		System.out.println("Editing section and calendar:\n" + newSection.toString() + newCal.toString());
		EditSectionReturnObject esro = sectionsService.editSection(courseSectionDto);
		
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("returnObject", esro);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keyMap);

		System.out.println(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
