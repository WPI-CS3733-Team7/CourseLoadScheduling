package org.dselent.scheduling.server.service;

import java.sql.SQLException;

import org.dselent.scheduling.server.dto.EditUserDto;
import org.dselent.scheduling.server.returnobject.AccountTabReturnObject;
import org.dselent.scheduling.server.returnobject.ChangePasswordReturnObject;
import org.dselent.scheduling.server.returnobject.EditUserReturnObject;
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
		
	public ChangePasswordReturnObject changePassword(String oldPassword, String newPassword, int userId) throws SQLException;
	public AccountTabReturnObject page(Integer userId) throws SQLException;
	public EditUserReturnObject editUser(EditUserDto editUserDto) throws SQLException;
	
}
