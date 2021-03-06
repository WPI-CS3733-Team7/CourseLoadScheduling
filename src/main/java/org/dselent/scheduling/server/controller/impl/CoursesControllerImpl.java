package org.dselent.scheduling.server.controller.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dselent.scheduling.server.controller.CoursesController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.requests.SelectCourse;
import org.dselent.scheduling.server.returnobject.SelectCourseReturnObject;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class CoursesControllerImpl implements CoursesController {

	@Autowired
    private CourseService courseService;
   
	public ResponseEntity<String> select(@RequestBody Map<String, String> request) throws Exception 
    {
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		Course newCourse = new Course();
		CalendarInfo newCalendarInfo = new CalendarInfo();
		
		newCourse.setId(Integer.parseInt(request.get(SelectCourse.getBodyName(SelectCourse.BodyKey.COURSE_ID))));
		newCalendarInfo.setCalTerm(request.get(SelectCourse.getBodyName(SelectCourse.BodyKey.TERM)));
		newCalendarInfo.setCalYear(Integer.parseInt(request.get(SelectCourse.getBodyName(SelectCourse.BodyKey.YEAR))));
				
		returnList.add(courseService.selectCourse(newCourse, newCalendarInfo));
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);
		
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<String> edit(@RequestBody Map<String, String> request) throws SQLException, JsonProcessingException
	{
		// add any objects that need to be returned to the success list
		String response = "";
		
		Course newCourse = new Course();

		System.out.println(request.toString());
		
		if(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.ID))!=null)
			newCourse.setId(Integer.parseInt(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.ID))));
		if(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME))!=null)
			newCourse.setCourseName(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME)));
		if(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NUMBER))!=null)
			newCourse.setCourseNumber(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NUMBER)));
		if(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.FREQUENCY))!=null)
			newCourse.setFrequency(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.FREQUENCY)));
		if(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.DELETED))!=null)
			newCourse.setDeleted(Boolean.parseBoolean(request.get(CourseEdit.getBodyName(CourseEdit.BodyKey.DELETED))));
		
		Course editedCourse = courseService.editCourse(newCourse);
		
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("returnObject", editedCourse);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keyMap);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
