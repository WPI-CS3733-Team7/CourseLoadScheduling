package org.dselent.scheduling.server.controller;

import java.util.Map;

import org.dselent.scheduling.server.requests.ChangePassword;
import org.dselent.scheduling.server.requests.AccountPage;
import org.dselent.scheduling.server.requests.UserEdit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/{user_id}/account/")
public interface AccountController {
	
    @RequestMapping(method=RequestMethod.POST, value=AccountPage.REQUEST_NAME)
    public ResponseEntity<String> page(@PathVariable("user_id") Integer userId) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=ChangePassword.REQUEST_NAME)
	public ResponseEntity<String> changePassword(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception;
    
    @RequestMapping(method=RequestMethod.POST, value=UserEdit.REQUEST_NAME)
    public ResponseEntity<String> editUser(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception;
}
