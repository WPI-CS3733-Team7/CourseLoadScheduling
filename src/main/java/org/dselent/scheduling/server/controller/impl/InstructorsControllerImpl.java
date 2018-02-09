package org.dselent.scheduling.server.controller.impl;

import java.util.Map;
import org.dselent.scheduling.server.controller.InstructorsController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.requests.InstructorEdit;
import org.dselent.scheduling.server.requests.SelectInstructor;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.dselent.scheduling.server.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for handling selecting, adding, and editing instructor objects in the database.
 * 
 * @author Leif Sahyun, Myo Min Thant
 */
@Controller
public class InstructorsControllerImpl implements InstructorsController
{
	@Autowired
    private InstructorService instructorService;
   
	public ResponseEntity<String> select(@RequestBody Map<String, String> request) throws Exception 
    {
		String response = "";
		/* Code for selecting an instructor goes here */
		
		Instructor newInstructor = new Instructor();
		CalendarInfo newCalendarInfo = new CalendarInfo();
		
		
		newInstructor.setId(Integer.parseInt(request.get(SelectInstructor.getParameterName(SelectInstructor.ParameterKey.INSTRUCTOR_ID))));
		newCalendarInfo.setCalTerm(request.get(SelectInstructor.getParameterName(SelectInstructor.ParameterKey.TERM)));
		newCalendarInfo.setCalYear(Integer.parseInt(request.get(SelectInstructor.getParameterName(SelectInstructor.ParameterKey.YEAR))));
		
		
		SelectInstructorReturnObject newSelectInstructor= instructorService.selectInstructor(newInstructor, newCalendarInfo);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, newSelectInstructor);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<String> edit(Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			Instructor newInstructor = new Instructor();
			
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.INSTRUCTOR_ID))!=null)
				newInstructor.setId(Integer.parseInt(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.INSTRUCTOR_ID))));
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.RANK))!=null)
				newInstructor.setRank(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.RANK)));
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.FIRST_NAME))!=null)
				newInstructor.setFirstName(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.FIRST_NAME)));
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.LAST_NAME))!=null)
				newInstructor.setLastName(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.LAST_NAME)));
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.EMAIL))!=null)
				newInstructor.setEmail(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.EMAIL)));
			if(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.DELETED))!=null)
				newInstructor.setDeleted(Boolean.parseBoolean(request.get(InstructorEdit.getBodyName(InstructorEdit.BodyKey.DELETED))));
			
			newInstructor = instructorService.editInstructor(newInstructor);
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, newInstructor);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}

	