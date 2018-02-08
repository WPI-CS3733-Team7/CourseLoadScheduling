package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.requests.SchedulerPage;
import org.dselent.scheduling.server.requests.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/{user_id}/scheduler/schedule")
public interface SchedulerScheduleController {
	
	@RequestMapping(method=RequestMethod.POST, value=SchedulerPage.REQUEST_NAME)
	public ResponseEntity<String> page(@PathVariable("user_id") Integer userId) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=Validate.REQUEST_NAME)
	public ResponseEntity<String> validate(@RequestParam(value = "year", required = true) Integer year) throws Exception;
	
}
