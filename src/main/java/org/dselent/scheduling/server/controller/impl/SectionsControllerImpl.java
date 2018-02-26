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
		
		CourseSection newSection = new CourseSection();
		CalendarInfo newCal = new CalendarInfo();
		
		/*
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.COURSE_ID), action.getCourseId());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.ID), action.getId());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.INSTRUCTOR_ID), action.getId());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.SECTION_NAME), action.getSectionName());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.SECTION_ID), action.getSectionId());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.SECTION_TYPE), action.getSectionType());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.POPULATION), action.getPopulation());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.YEAR), action.getYear());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.TERM), action.getTerm());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.DAYS), action.getDays());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.START_TIME), action.getStartTime());
		JSONHelper.putStringValue(jsonObject, JSONHelper.convertKeyName(SendEditSectionKeys.END_TIME), action.getEndTime());
		*/
		
		Integer id = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.ID)));
		Integer courseId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.COURSE_ID)));
		Integer instructorId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.INSTRUCTOR_ID)));
		Integer calendarInfoId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.CALENDAR_INFO_ID)));
		String sectionName = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NAME));
		Integer sectionId = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_ID)));
		String sectionType = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_TYPE));
		String population = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.POPULATION));
		Integer year = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.YEAR)));
		String term = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.TERM));
		String days = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DAYS));
		Integer startTime = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.START_TIME)));
		Integer endTime = Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.END_TIME)));
		Boolean deleted = Boolean.parseBoolean(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DELETED)));
		
		CourseSectionDto.Builder builder = CourseSectionDto.builder();
		CourseSectionDto courseSectionDto = builder.withSection(newSection)
		.withCal(newCal)
		.withInstructorId(instructorId)
		.withCourseId(courseId)
		.build();
		
		EditSectionReturnObject esro = sectionsService.editSection(courseSectionDto);
		
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("returnObject", esro);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keyMap);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
