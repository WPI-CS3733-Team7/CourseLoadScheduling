package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.controller.SchedulerScheduleController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.service.SchedulerScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SchedulerScheduleControllerImpl implements SchedulerScheduleController {

	@Autowired
	private SchedulerScheduleService schedulerScheduleService;
	
	public ResponseEntity<String> validate(@RequestParam Integer year) throws Exception {
		
		// add any objects that need to be returned to the returnList
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		returnList.add(schedulerScheduleService.validate(year));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> page(@PathVariable("user_id") Integer userId) throws Exception {
		
		// add any objects that need to be returned to the returnList
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		returnList.add(schedulerScheduleService.page(userId));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
