package org.dselent.scheduling.server.controller.impl;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import org.dselent.scheduling.server.controller.CoursesController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.Model;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.requests.InstructorEdit;
import org.dselent.scheduling.server.requests.SelectCourse;
import org.dselent.scheduling.server.requests.SelectInstructor;
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
		List<Model> responseObjectList = new ArrayList<>();
		
		Course newCourse = new Course();
		CalendarInfo newCalendarInfo = new CalendarInfo();
		
		
		newCourse.setId(Integer.parseInt(request.get(SelectCourse.getParameterName(SelectCourse.ParameterKey.COURSE_ID))));
		newCalendarInfo.setCalTerm(request.get(SelectCourse.getParameterName(SelectCourse.ParameterKey.TERM)));
		newCalendarInfo.setCalYear(Integer.parseInt(request.get(SelectCourse.getParameterName(SelectCourse.ParameterKey.YEAR))));
		
		courseService.selectCourse(newCourse, newCalendarInfo);
		responseObjectList.add(newCourse);
		responseObjectList.add(newCalendarInfo);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, responseObjectList);
		
		
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
