package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Click;
import org.dselent.scheduling.server.requests.Login;
import org.dselent.scheduling.server.requests.Register;
//import org.dselent.scheduling.server.requests.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/scheduler/schedule")
public class SchedulerScheduleController {
	
	//@RequestMapping(method=RequestMethod.POST, value=Click.REQUEST_NAME)
	//public ResponseEntity<String> click(@RequestBody Map<String, String> request) throws Exception;
    
   // @RequestMapping(method=RequestMethod.POST, value=Validate.REQUEST_NAME)
	//public ResponseEntity<String> validate(@RequestBody Map<String, String> request) throws Exception;
	
}
