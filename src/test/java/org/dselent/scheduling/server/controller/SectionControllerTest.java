package org.dselent.scheduling.server.controller;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.requests.SectionEdit;
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
public class SectionControllerTest {
	
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
	public void testEdit() throws Exception 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_ID), null);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NAME), "A01");
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_NUMBER), 01);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.SECTION_TYPE), "LAB");
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.COURSE_NAME), "Introduction to Programming for Non-Majors");
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.POPULATION), 100);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.DAYS), "MWTF");
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.START_TIME), 900);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.END_TIME), 1050);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.TERM), "A");
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.YEAR), 2019);
		jsonObject.put(SectionEdit.getBodyName(SectionEdit.BodyKey.DELETED), false);
		String jsonString = jsonObject.toString();
		
		this.mockMvc.perform(post("/10/scheduler/sections/edit").content(jsonString)
	    .contentType(MediaType.APPLICATION_JSON_VALUE)
	    .characterEncoding("utf-8"))
	    .andDo(MockMvcResultHandlers.print())
	    .andExpect(status().isOk());
	}
}