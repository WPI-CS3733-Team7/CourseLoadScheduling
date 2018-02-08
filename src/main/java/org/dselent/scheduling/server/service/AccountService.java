package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.EditUserDto;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.AccountTabReturnObject;
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
		
	public ChangePasswordReturnObject changePassword(String oldPassword, String newPassword, int userId) throws SQLException;
	public AccountTabReturnObject page(Integer userId) throws SQLException;
	public List<User> editUser(EditUserDto editUserDto) throws SQLException;
	
}
