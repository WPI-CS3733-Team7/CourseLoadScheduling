package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dto.EditUserDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UserRole;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.service.AccountService;
import org.dselent.scheduling.server.returnobject.AccountTabReturnObject;
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
	
	@Autowired
	private CustomDao customDao;
	
	@Autowired
	private UserRolesDao userRolesDao;
	
	@Autowired
	private UsersRolesLinksDao usersRolesLinksDao;
	
	@Autowired
	private InstructorUserLinksDao instructorUserLinksDao;
	
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
	    		return new ChangePasswordReturnObject(user.getEncryptedPassword(),"SUCCESS");
	    	} else {
	    		failureCpro.setMessage("FAILURE");
	    		return failureCpro;
	    	}
	}

	@Override
	public AccountTabReturnObject page(Integer userId) throws SQLException
	{
		
		// get user and input info into return object
		User user = usersDao.findById(userId);
		
		AccountTabReturnObject atro = 
			new AccountTabReturnObject(user.getFirstName(), user.getLastName(), user.getUserName(), user.getUserStateId(), user.getEmail(), user.getEncryptedPassword(), null);
		
		// get admin's id
		String selectRoleColumnName = UserRole.getColumnName(UserRole.Columns.ROLE_NAME);
		String selectRole = "ADMIN";
		
		List<QueryTerm> selectRoleQueryTermList = new ArrayList<>();
		
		QueryTerm selectRoleTerm = new QueryTerm();
		selectRoleTerm.setColumnName(selectRoleColumnName);
		selectRoleTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectRoleTerm.setValue(selectRole);
    		selectRoleQueryTermList.add(selectRoleTerm);
		
    		List<String> selectRoleColumnNameList = UserRole.getColumnNameList();
    		
		List<UserRole> userRoles = userRolesDao.select(selectRoleColumnNameList, selectRoleQueryTermList, null);
		
		// check whether user is an administrator
		boolean admin = false;
		List<User> admins = customDao.getAllUsersWithRole(userRoles.get(0).getId());
		
		for (int i = 0; i < admins.size(); i++) {
			if (admins.get(i).getId() == user.getId()) {
				admin = true;
				break;
			}
		}
		
		// if admin, pass back list of users
		if (admin == true) {
			String selectColumnName = User.getColumnName(User.Columns.ID);
			Integer selectUserId = -1;
	    	
			List<QueryTerm> selectQueryTermList = new ArrayList<>();
	    	
			QueryTerm selectUseNameTerm = new QueryTerm();
			selectUseNameTerm.setColumnName(selectColumnName);
			selectUseNameTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
	    		selectUseNameTerm.setValue(selectUserId);
	    		selectQueryTermList.add(selectUseNameTerm);
	    	
	    		List<String> selectColumnNameList = User.getColumnNameList();
	    		
	    		String userSortColumnName = User.getColumnName(User.Columns.FIRST_NAME);
        		List<Pair<String, ColumnOrder>> userOrderByList = new ArrayList<>();
            	Pair<String, ColumnOrder> userOrderPair = new Pair<String, ColumnOrder>(userSortColumnName, ColumnOrder.ASC);
            	userOrderByList.add(userOrderPair);
            	
			List<User> selectedUserList = usersDao.select(selectColumnNameList, selectQueryTermList, userOrderByList);
			atro.setUserList(selectedUserList);
		} else {
			atro.setUserList(new ArrayList<User>());
		}

		return atro;
	}	
    
    @Transactional
    public List<User> editUser(EditUserDto editUserDto) throws SQLException {
    		
    		User editUser = usersDao.findById(editUserDto.getEditId());
    		if (editUser == null)
    		{
    			return selectAllUsers();
    		} 
    		
    		if (editUserDto.getDeleted() == true)
    		{
    			String selectColumnName = User.getColumnName(User.Columns.ID);
    			Integer selectUserId = editUser.getId();
    	    	
    			List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	    	
    			QueryTerm selectUseNameTerm = new QueryTerm();
    			selectUseNameTerm.setColumnName(selectColumnName);
    			selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	    		selectUseNameTerm.setValue(selectUserId);
    	    		selectQueryTermList.add(selectUseNameTerm);
    			
    			usersDao.delete(selectQueryTermList);
    		} else {
    			
    			// update userRolesLinks table
	    		String updateRoleColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.USER_ID);
	    	    	Integer userId = editUser.getId();
	    	    	Integer newRoleId = editUserDto.getUserRole();
	    	    	List<QueryTerm> updateRoleQueryTermList = new ArrayList<>();
	    	    	
	    	    	QueryTerm updateRoleTerm = new QueryTerm();
	    	    	updateRoleTerm.setColumnName(updateRoleColumnName);
	    	    	updateRoleTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	    	    	updateRoleTerm.setValue(userId);
	    	    	updateRoleQueryTermList.add(updateRoleTerm);
	    	    	
	    	    	usersRolesLinksDao.update(updateRoleColumnName, newRoleId, updateRoleQueryTermList);
    			
    			// update instructorUserLinks table
	    	    	String updateColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID);
	    	    	Integer newInstructorId = editUserDto.getLinkedInstructorId();
	    	    	List<QueryTerm> updateInstructorQueryTermList = new ArrayList<>();
	    	    	
	    	    	QueryTerm updateInstructorIdTerm = new QueryTerm();
	    	    	updateInstructorIdTerm.setColumnName(updateColumnName);
	    	    	updateInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	    	    	updateInstructorIdTerm.setValue(newInstructorId);
	    	    	updateInstructorQueryTermList.add(updateInstructorIdTerm);
	    	    	
	    	    	instructorUserLinksDao.update(updateColumnName, newInstructorId, updateInstructorQueryTermList);
    		}
    		
    		return selectAllUsers();
    }
    
    private List<User> selectAllUsers() throws SQLException
    {
    		String selectColumnName = User.getColumnName(User.Columns.ID);
		Integer selectUserId = -1;
    	
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
		QueryTerm selectUseNameTerm = new QueryTerm();
		selectUseNameTerm.setColumnName(selectColumnName);
		selectUseNameTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
    		selectUseNameTerm.setValue(selectUserId);
    		selectQueryTermList.add(selectUseNameTerm);
    	
    		List<String> selectColumnNameList = User.getColumnNameList();
    		
    		String userSortColumnName = User.getColumnName(User.Columns.FIRST_NAME);
    		List<Pair<String, ColumnOrder>> userOrderByList = new ArrayList<>();
        	Pair<String, ColumnOrder> userOrderPair = new Pair<String, ColumnOrder>(userSortColumnName, ColumnOrder.ASC);
        	userOrderByList.add(userOrderPair);
        	
		return usersDao.select(selectColumnNameList, selectQueryTermList, userOrderByList);
    }
}
