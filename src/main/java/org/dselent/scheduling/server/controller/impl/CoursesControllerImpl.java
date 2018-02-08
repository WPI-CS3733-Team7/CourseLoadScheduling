package org.dselent.scheduling.server.controller.impl;

import java.sql.SQLException;
import java.util.Map;

import org.dselent.scheduling.server.controller.CoursesController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CoursesControllerImpl implements CoursesController {

	@Autowired
    private CourseService courseService;
   
	public ResponseEntity<String> select(@RequestBody Map<String, String> request) throws Exception 
    {
		String response = "";
		/* Code for selecting an instructor goes here */
		return new ResponseEntity<String>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<String> edit(Map<String, String> request) throws SQLException, JsonProcessingException {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			Course newCourse = new Course();
			
			newCourse.setId(Integer.parseInt(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_ID))));
			newCourse.setCourseName(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME)));
			newCourse.setCourseNumber(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME)));
			newCourse.setFrequency(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.FREQUENCY)));
			newCourse.setDeleted(Boolean.parseBoolean(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.DELETED))));
			
			newCourse = courseService.editCourse(newCourse);
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, newCourse);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
