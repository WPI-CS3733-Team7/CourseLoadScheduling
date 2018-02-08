package org.dselent.scheduling.server.controller;

import java.util.Map;
import org.dselent.scheduling.server.requests.ChangePassword;
import org.dselent.scheduling.server.requests.Click;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/{user_id}/account")
public interface AccountController {
	
    @RequestMapping(method=RequestMethod.POST, value=Click.REQUEST_NAME)
    public ResponseEntity<String> click( @RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=ChangePassword.REQUEST_NAME)
	public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) throws Exception;
}
