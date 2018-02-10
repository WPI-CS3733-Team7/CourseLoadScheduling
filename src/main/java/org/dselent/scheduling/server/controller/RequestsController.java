package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.Click;
import org.dselent.scheduling.server.requests.RequestsPage;
import org.dselent.scheduling.server.requests.Submit;
import org.dselent.scheduling.server.requests.SubmitRequest;
import org.dselent.scheduling.server.requests.SubmitResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/requests")
@Controller
public interface RequestsController {
    @RequestMapping(method=RequestMethod.POST, value=RequestsPage.REQUEST_NAME)
    public ResponseEntity<String> page(@PathVariable("user_id") Integer id) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=SubmitRequest.REQUEST_NAME)
    public ResponseEntity<String> submitRequest(@PathVariable("user_id") int userId, @RequestBody Map<String, String> request) throws Exception;

    @RequestMapping(method=RequestMethod.POST, value=SubmitResponse.REQUEST_NAME)
    public ResponseEntity<String> submitResponse(@RequestBody Map<String, String> request) throws Exception;

}
