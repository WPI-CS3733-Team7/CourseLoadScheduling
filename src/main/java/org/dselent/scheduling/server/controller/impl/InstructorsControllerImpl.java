package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
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
   
	public ResponseEntity<String> select(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception 
    {
		// add any objects that need to be returned to the success list
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		Instructor newInstructor = new Instructor();
		CalendarInfo newCalendarInfo = new CalendarInfo();
		
		System.out.println(request.get(SelectInstructor.getBodyName(SelectInstructor.BodyKey.INSTRUCTOR_ID)));
		newInstructor.setId(Integer.parseInt(request.get(SelectInstructor.getBodyName(SelectInstructor.BodyKey.INSTRUCTOR_ID))));
		newCalendarInfo.setCalTerm(request.get(SelectInstructor.getBodyName(SelectInstructor.BodyKey.TERM)));
		newCalendarInfo.setCalYear(Integer.parseInt(request.get(SelectInstructor.getBodyName(SelectInstructor.BodyKey.YEAR))));
		
		returnList.add(instructorService.selectInstructor(newInstructor, newCalendarInfo));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<String> edit(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception
	{
		// add any objects that need to be returned to the success list
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
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

		returnList.add(instructorService.editInstructor(newInstructor));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}

	