package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Register;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/scheduler/sections")
@Controller
public interface SectionsController
{
    @RequestMapping(method=RequestMethod.POST, value=Register.REQUEST_NAME)
	public ResponseEntity<String> edit(@RequestBody Map<String, String> request) throws Exception;
}

	