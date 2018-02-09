package org.dselent.scheduling.server.controller;

import java.sql.SQLException;
import java.util.Map;

import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.SelectCourse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;

@RequestMapping("/scheduler/courses")
public interface CoursesController
{
    @RequestMapping(method=RequestMethod.POST, value=SelectCourse.REQUEST_NAME)
	public ResponseEntity<String> select(@RequestBody Map<String, String> request) throws Exception;

    @RequestMapping(method=RequestMethod.POST, value=CourseEdit.REQUEST_NAME)
	public ResponseEntity<String> edit(@RequestBody Map<String, String> request) throws SQLException, JsonProcessingException;
}

	