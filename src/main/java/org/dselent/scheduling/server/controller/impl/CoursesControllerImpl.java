package org.dselent.scheduling.server.controller.impl;

import java.util.Map;

import org.dselent.scheduling.server.controller.CoursesController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.requests.InstructorEdit;
import org.dselent.scheduling.server.service.CourseService;
import org.dselent.scheduling.server.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

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
	public ResponseEntity<String> edit(Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			Instructor newInstructor = new Instructor();
			
			newInstructor.setId(Integer.parseInt(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.INSTRUCTOR_ID))));
			newInstructor.setRank(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.RANK)));
			newInstructor.setRank(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.FIRST_NAME)));
			newInstructor.setRank(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.LAST_NAME)));
			newInstructor.setRank(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.EMAIL)));
			newInstructor.setDeleted(Boolean.parseBoolean(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.DELETED))));
			
			courseService.editCourse(newInstructor);
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, newInstructor);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
