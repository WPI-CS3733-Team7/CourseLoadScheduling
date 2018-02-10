package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.requests.InstructorEdit;
import org.dselent.scheduling.server.requests.SelectInstructor;
import org.dselent.scheduling.server.requests.SubmitRequest;
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
public class RequestsControllerTest {
	
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
	public void testSubmit() throws Exception 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SubmitRequest.getBodyName(SubmitRequest.BodyKey.REQUEST_TYPE), "Course");
		jsonObject.put(SubmitRequest.getBodyName(SubmitRequest.BodyKey.REQUEST_DETAILS), "Gimme a good one");
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/requests/submit/request").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
}
