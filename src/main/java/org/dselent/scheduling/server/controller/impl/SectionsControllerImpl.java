package org.dselent.scheduling.server.controller.impl;

import java.sql.SQLException;
import java.util.Map;

import org.dselent.scheduling.server.controller.SectionsController;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.requests.SectionEdit;
import org.dselent.scheduling.server.service.SectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SectionsControllerImpl implements SectionsController{
	
	@Autowired
    private SectionsService sectionsService;
	
	public ResponseEntity<String> edit(@RequestBody Map<String, String> request) throws SQLException, JsonProcessingException {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			CourseSection newSection = new CourseSection();
			CalendarInfo newCal = new CalendarInfo();
			String courseName = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.COURSE_NAME));
			String instructorName = request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.INSTRUCTOR_NAME));
			
			String instNames[] = new String[2];
			if(instructorName!=null)
				instNames = instructorName.split(instructorName);
			else {
				instNames[0] = null;
				instNames[1] = null;
			}
			
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_ID))!=null)
				newSection.setId(Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_ID))));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.POPULATION))!=null)
				newSection.setPopulation(Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.POPULATION))));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NAME))!=null)
				newSection.setSectionName(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NAME)));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_TYPE))!=null)
				newSection.setSectionType(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_TYPE)));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DELETED))!=null)
				newSection.setDeleted(Boolean.parseBoolean(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DELETED))));
			
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.TERM))!=null)
				newCal.setCalTerm(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.TERM)));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.YEAR))!=null)
				newCal.setCalYear(Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.YEAR))));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DAYS))!=null)
				newCal.setDays(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.DAYS)));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.START_TIME))!=null)
				newCal.setStartTime(Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.START_TIME))));
			if(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.END_TIME))!=null)
				newCal.setEndTime(Integer.parseInt(request.get(SectionEdit.getBodyName(SectionEdit.BodyKey.END_TIME))));
			
			CourseSectionDto.Builder builder = CourseSectionDto.builder();
			CourseSectionDto courseSectionDto = builder.withSection(newSection)
			.withCal(newCal)
			.withInstFirstName(instNames[0])
			.withInstLastName(instNames[1])
			.withCourseName(courseName)
			.build();
			
			newSection = sectionsService.editSection(courseSectionDto);
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, newSection);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
