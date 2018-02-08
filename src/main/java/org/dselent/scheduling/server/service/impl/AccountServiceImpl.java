package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.service.AccountService;
import org.dselent.scheduling.server.returnobject.ChangePasswordReturnObject;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private UsersDao usersDao;
	
	public AccountServiceImpl()
    {
    	//
    }
    
    /*
     * (non-Javadoc)
     * @see org.dselent.scheduling.server.service.UserService#registerUser(org.dselent.scheduling.server.dto.RegisterUserDto)
     */
    @Transactional
	public ChangePasswordReturnObject changePassword(String oldPassword, String newPassword, int userId) throws SQLException {
    	
    	User user = usersDao.findById(userId);
    	
    	ChangePasswordReturnObject failureCpro = new ChangePasswordReturnObject("","");
    	
    	String selectColumnName = User.getColumnName(User.Columns.ENCRYPTED_PASSWORD);
		String selectEncryptedPassword = oldPassword;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
		QueryTerm selectedEncryptedPasswordTerm = new QueryTerm();
		selectedEncryptedPasswordTerm.setColumnName(selectColumnName);
		selectedEncryptedPasswordTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectedEncryptedPasswordTerm.setValue(selectEncryptedPassword);
		selectQueryTermList.add(selectedEncryptedPasswordTerm);
    	
    	if(user.getEncryptedPassword().equals(oldPassword)) {
    		usersDao.update("encryptedPassword", newPassword, selectQueryTermList);
    		return new ChangePasswordReturnObject(newPassword,"SUCCESS");
    	} else {
    		failureCpro.setMessage("FAILURE");
    		return failureCpro;
    	}
    	
    	
    	
    	// If yes, call update to change old password to new password
    	// If no, return error message
    	
		// TODO Auto-generated method stub
	}	
    
    @Transactional
    public List<User> editUser() throws SQLException {
    	return null; 
    }
}
