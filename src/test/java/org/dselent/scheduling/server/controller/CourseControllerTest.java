package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.CourseEdit;
import org.dselent.scheduling.server.requests.SelectInstructor;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CourseControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup()
	{
		// initializes controllers and dependencies
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	/*
	@Test
	public void testSelect() throws Exception 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SelectCourse.getBodyName(SelectCourse.BodyKey.COURSE_ID), 2);
		jsonObject.put(SelectCourse.getBodyName(SelectCourse.BodyKey.TERM), "A");
		jsonObject.put(SelectCourse.getBodyName(SelectCourse.BodyKey.YEAR), 2018);
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/scheduler/courses/select").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
	*/
	
	@Test
	public void testEdit() throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CourseEdit.getBodyName(CourseEdit.BodyKey.ID), 84);
		jsonObject.put(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NAME), "Introduction to Programming for Pros");
		jsonObject.put(CourseEdit.getBodyName(CourseEdit.BodyKey.COURSE_NUMBER), "53421");
		jsonObject.put(CourseEdit.getBodyName(CourseEdit.BodyKey.FREQUENCY), "2 per semester");
		jsonObject.put(CourseEdit.getBodyName(CourseEdit.BodyKey.DELETED), true);
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/scheduler/courses/edit").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
	
}