package org.dselent.scheduling.server.controller.impl;

import java.util.HashMap;
import java.util.Map;

import org.dselent.scheduling.server.controller.SchedulerScheduleController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.returnobject.SchedulerTabReturnObject;
import org.dselent.scheduling.server.returnobject.ValidateReturnObject;
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
		
		ValidateReturnObject vro = schedulerScheduleService.validate(year);
		
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("returnObject", vro);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keyMap);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> page(@PathVariable("user_id") Integer userId) throws Exception {
		
		// add any objects that need to be returned to the returnList
		String response = "";
		
		SchedulerTabReturnObject stro = schedulerScheduleService.page(userId);
		
		Map<String, Object> keyMap = new HashMap<>();
		keyMap.put("returnObject", stro);
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, keyMap);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
}
