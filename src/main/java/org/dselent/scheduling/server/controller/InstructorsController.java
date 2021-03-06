package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.InstructorEdit;
import org.dselent.scheduling.server.requests.SelectInstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/scheduler/instructors")
@Controller
public interface InstructorsController
{
    @RequestMapping(method=RequestMethod.POST, value=SelectInstructor.REQUEST_NAME)
	public ResponseEntity<String> select(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception;

    @RequestMapping(method=RequestMethod.POST, value=InstructorEdit.REQUEST_NAME)
	public ResponseEntity<String> edit(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception;
}

	