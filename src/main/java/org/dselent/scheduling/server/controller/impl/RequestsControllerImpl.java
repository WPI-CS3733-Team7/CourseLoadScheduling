package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.RequestsController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.model.Request;
import org.dselent.scheduling.server.model.RequestType;
import org.dselent.scheduling.server.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.dselent.scheduling.server.requests.SubmitRequest;
import org.dselent.scheduling.server.requests.SubmitResponse;

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
	
	public ResponseEntity<String> submitRequest(@PathVariable("user_id") int userId, @RequestBody Map<String, String> request) throws Exception 
	{

		// add any objects that need to be returned to the success list
		String response = "";
		Request newRequest = new Request();
		RequestType newRequestType = new RequestType();
		
		newRequestType.setRequestType(request.get(SubmitRequest.getBodyName(SubmitRequest.BodyKey.REQUEST_TYPE)));
		newRequest.setRequestDetails(request.get(SubmitRequest.getBodyName(SubmitRequest.BodyKey.REQUEST_DETAILS)));

		List<Request> selectedRequest = requestsService.submitRequest(userId, newRequest);
		
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, selectedRequest);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> submitResponse(Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub

		// add any objects that need to be returned to the success list
		String response = "";

		Request newRequest = new Request();
		
		newRequest.setId(Integer.parseInt(request.get(SubmitResponse.getParameterName(SubmitResponse.ParameterKey.REQUEST_ID))));
		newRequest.setReplyTypeId(Integer.parseInt(request.get(SubmitResponse.getParameterName(SubmitResponse.ParameterKey.REPLY_TYPE))));
		
		List<Request> selectedRequests = requestsService.submitResponse(newRequest.getId(), newRequest.getReplyTypeId());
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, selectedRequests);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
