package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.ChangePasswordReturnObject;
import org.springframework.stereotype.Service;

/**
 * Service layer to specify all business logic. Calls the dao layer when data retrieval is needed.
 * Interfaces specify the behavior and the implementation provide the specific implementations.
 * 
 * @author John Amaral
 *
 */
@Service
public interface AccountService {
	
	/**
	 * Changes password of the user in system
	 * Performs an update on the password column of the users table
	 * @throws SQLException 
	 */
	
	public ChangePasswordReturnObject changePassword(String oldPassword, String newPassword, int userId) throws SQLException;

	//click account page
	//change password
	//edit user
	
	public List<User> editUser() throws SQLException;
	
}
