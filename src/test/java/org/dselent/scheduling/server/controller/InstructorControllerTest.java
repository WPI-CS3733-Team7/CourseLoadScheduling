package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.requests.InstructorEdit;
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
public class InstructorControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup()
	{
		// initializes controllers and dependencies
	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	public void testSelect() throws Exception 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SelectInstructor.getBodyName(SelectInstructor.BodyKey.INSTRUCTOR_ID), 33);
		jsonObject.put(SelectInstructor.getBodyName(SelectInstructor.BodyKey.TERM), "A");
		jsonObject.put(SelectInstructor.getBodyName(SelectInstructor.BodyKey.YEAR), 2018);
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/scheduler/instructors/select").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
	
	/*
	@Test
	public void testEdit() throws Exception
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.INSTRUCTOR_ID), 74);
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.FIRST_NAME), "Beast");
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.LAST_NAME), "Manson");
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.RANK), "Overlord");
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.EMAIL), "jplanson@wpi.edu");
		jsonObject.put(InstructorEdit.getBodyName(InstructorEdit.BodyKey.DELETED), true);
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/scheduler/instructors/edit").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
	*/
}
