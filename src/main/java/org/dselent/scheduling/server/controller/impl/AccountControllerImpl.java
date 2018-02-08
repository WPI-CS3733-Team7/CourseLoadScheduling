package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.AccountController;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.AccountPage;
import org.dselent.scheduling.server.requests.ChangePassword;
import org.dselent.scheduling.server.requests.UserEdit;
import org.dselent.scheduling.server.returnobject.ChangePasswordReturnObject;
import org.dselent.scheduling.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller for handling requests related to account info such as clicking on the account tag, changing the password,
 * or editing a user. Controller methods are the first methods reached by the request server-side (with special exception).
 * They parse the request and then call the appropriate service method to execute the business logic.
 * Any results or data is then sent back to the client.
 * 
 * @author John Amaral
 */
@Controller
public class AccountControllerImpl implements AccountController {

	@Autowired
    private AccountService accountService;
    
	/**
	 * 
	 * @param request The body of the request expected to contain a map of String key-value pairs
	 * @return A ResponseEntity for the response object(s) and the status code
	 * @throws Exception 
	 */
	
	public ResponseEntity<String> clickAccountPage(Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			//String userName = request.get(AccountPage.getBodyName(AccountPage.BodyKey.USER_NAME));
			//String password = request.get(Login.getBodyName(Login.BodyKey.PASSWORD));
			
			//LoginUserReturnObject luro = userService.loginUser(userName, password);
			//response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, luro);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> changePassword(@PathVariable("user_id") int userId, @RequestBody Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			
			String oldPassword = request.get(ChangePassword.getBodyName(ChangePassword.BodyKey.OLD_PASSWORD));
			String newPassword = request.get(ChangePassword.getBodyName(ChangePassword.BodyKey.NEW_PASSWORD));
			
			// get userId from URI
			// send old and new password to service layer method
			// in method: check whether oldPassword equals password associated with userId
			// if yes, success, and change password in database to newPassword (using update)
			// if no, return error message
			
			//oldPassword.equals(newPassword);
			
			ChangePasswordReturnObject cpro = accountService.changePassword(oldPassword, newPassword, userId);
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, cpro);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> editUser(@PathVariable("user_id") int userId, Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
			String response = "";
			List<Object> returnList = new ArrayList<Object>();
			
			//Integer userRole = request.get(UserEdit.getBodyName(UserEdit.BodyKey.USER_ROLE));
			//Integer linkedInstructor = request.get(UserEdit.getBodyName(UserEdit.BodyKey.LINKED_INSTRUCTOR));
			
			//
			// returnList.add();
			
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

			return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
