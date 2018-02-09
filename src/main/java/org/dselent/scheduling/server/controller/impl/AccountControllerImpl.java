package org.dselent.scheduling.server.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dselent.scheduling.server.controller.AccountController;
import org.dselent.scheduling.server.dto.EditUserDto;
import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.dselent.scheduling.server.requests.AccountPage;
import org.dselent.scheduling.server.requests.ChangePassword;
import org.dselent.scheduling.server.requests.Login;
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

	@Override
	public ResponseEntity<String> page(@PathVariable("user_id") Integer userId) throws Exception {
		
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		returnList.add(accountService.page(userId));
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	public ResponseEntity<String> changePassword(@PathVariable("user_id") Integer userId, @RequestBody Map<String, String> request) throws Exception {
		// TODO Auto-generated method stub
		
		// add any objects that need to be returned to the success list
		String response = "";
		
		String oldPassword = request.get(ChangePassword.getBodyName(ChangePassword.BodyKey.OLD_PASSWORD));
		String newPassword = request.get(ChangePassword.getBodyName(ChangePassword.BodyKey.NEW_PASSWORD));
		
		ChangePasswordReturnObject cpro = accountService.changePassword(oldPassword, newPassword, userId);
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, cpro);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> editUser(@PathVariable("user_id") Integer userId, Map<String, String> request) throws Exception {
		
		// add any objects that need to be returned to the success list
		String response = "";
		List<Object> returnList = new ArrayList<Object>();
		
		String editUserIdString = request.get(UserEdit.getBodyName(UserEdit.BodyKey.EDIT_ID));
		String userRoleString = request.get(UserEdit.getBodyName(UserEdit.BodyKey.USER_ROLE));
		String linkedInstructorIdString = request.get(UserEdit.getBodyName(UserEdit.BodyKey.LINKED_INSTRUCTOR));
		String deletedString = request.get(UserEdit.getBodyName(UserEdit.BodyKey.DELETED));
		
		Integer editUserId = Integer.parseInt(editUserIdString);
		Integer userRole = Integer.parseInt(userRoleString);
		Integer linkedInstructorId = Integer.parseInt(linkedInstructorIdString);
		Boolean deleted = Boolean.parseBoolean(deletedString);
		
		EditUserDto.Builder builder = EditUserDto.builder();
		EditUserDto editUserDto = builder.withEditId(editUserId)
		.withUserRole(userRole)
		.withLinkedInstructorId(linkedInstructorId)
		.withDeleted(deleted)
		.build();
		
		returnList.add(accountService.editUser(editUserDto));
		
		//EditUserDto editUser;
		
		//LoginUserReturnObject luro = userService.loginUser(userName, password);
		
		
		response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.SUCCESS, returnList);	

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	
}
