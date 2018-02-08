package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.RequestsController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RequestsControllerImpl implements RequestsController{

	@Autowired
	RequestsService requestsService;
	
	@Override
	public ResponseEntity<String> page(Integer userId) throws Exception {
		
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		returnList.add(requestsService.page(userId));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> submit(Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
