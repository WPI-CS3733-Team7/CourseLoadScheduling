package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
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
import org.dselent.scheduling.server.returnobject.EditUserReturnObject;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private InstructorsDao instructorsDao;
	
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
	    	
	    	ChangePasswordReturnObject failureCpro = new ChangePasswordReturnObject("");
	    	
	    	String selectColumnName = User.getColumnName(User.Columns.ENCRYPTED_PASSWORD);
	    	String selectEncryptedPassword = oldPassword;
	    	
	    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
	    	
		QueryTerm selectedEncryptedPasswordTerm = new QueryTerm();
		selectedEncryptedPasswordTerm.setColumnName(selectColumnName);
		selectedEncryptedPasswordTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectedEncryptedPasswordTerm.setValue(selectEncryptedPassword);
		selectQueryTermList.add(selectedEncryptedPasswordTerm);
	    	
		// oldPassword is plain text - encrypt with salt and check with getEncryptedPassword
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
	    	if(passwordEncoder.matches(oldPassword + user.getSalt(), user.getEncryptedPassword())) {
	    		usersDao.update("encryptedPassword", newPassword, selectQueryTermList);
	    		return new ChangePasswordReturnObject("SUCCESS: password updated.");
	    	} else {
	    		failureCpro.setMessage("ERROR: old password incorrect.");
	    		return failureCpro;
	    	}
	}

	@Override
	public AccountTabReturnObject page(Integer userId) throws SQLException
	{
		// get user and input info into return object
		User user = usersDao.findById(userId);
		
		AccountTabReturnObject atro = new AccountTabReturnObject(user.getFirstName(), user.getLastName(), user.getUserName(), null, user.getEmail(), null, null, null, null);
	
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get all User_Roles_Links that aren't deleted
		String selectUserRoleLinkColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.DELETED);
		Boolean isNotDeleted = false;
		
		List<QueryTerm> selectUserRoleLinkQueryTermList = new ArrayList<>();
		
		QueryTerm selectUserRoleLinkTerm = new QueryTerm();
		selectUserRoleLinkTerm.setColumnName(selectUserRoleLinkColumnName);
		selectUserRoleLinkTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectUserRoleLinkTerm.setValue(isNotDeleted);
    		selectUserRoleLinkQueryTermList.add(selectUserRoleLinkTerm);
		
    		List<String> selectUserRoleLinkColumnNameList = UsersRolesLink.getColumnNameList();
    		
		List<UsersRolesLink> userRoleLinkList = usersRolesLinksDao.select(selectUserRoleLinkColumnNameList, selectUserRoleLinkQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		
		int userRoleId = -1;
		for (int i = 0; i < userRoleLinkList.size(); i++) {
			if (userRoleLinkList.get(i).getUserId() == userId) {
				userRoleId = userRoleLinkList.get(i).getRoleId();
			}
		}
		atro.setUserRoleLinkList(userRoleLinkList);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get role name associated with user's role id
		String selectRoleColumnName = UserRole.getColumnName(UserRole.Columns.DELETED);
		
		List<QueryTerm> selectRoleQueryTermList = new ArrayList<>();
		
		QueryTerm selectRoleTerm = new QueryTerm();
		selectRoleTerm.setColumnName(selectRoleColumnName);
		selectRoleTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectRoleTerm.setValue(isNotDeleted);
    		selectRoleQueryTermList.add(selectRoleTerm);
		
    		List<String> selectRoleColumnNameList = UserRole.getColumnNameList();
    		
		List<UserRole> userRoles = userRolesDao.select(selectRoleColumnNameList, selectRoleQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		
		String userRoleName = "";
		for (int i = 0; i < userRoles.size(); i++) {
			if(userRoles.get(i).getId() == userRoleId) {
				userRoleName = userRoles.get(i).getRoleName();
			}
		}
		atro.setUserRole(userRoleName);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    	
		// get a list of all users
		List<User> userList = selectAllUsers(); 
		atro.setUserList(userList);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get a list of all instructors who aren't deleted
		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
		
		List<String> selectInstructorColumnNameList = Instructor.getColumnNameList();
		
		QueryTerm notDeleted = notDeleted(Instructor.getColumnName(Instructor.Columns.DELETED));
		notDeleted.setLogicalOperator(null);
		
		selectInstructorQueryTermList.add(notDeleted);
		
		List<Instructor> instructorList = instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		atro.setInstructorList(instructorList);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get a list of all instructor_user_links that aren't deleted
		List<QueryTerm> selectInstructorLinkQueryTermList = new ArrayList<>();
		
		List<String> selectInstructorLinkColumnNameList = InstructorUserLink.getColumnNameList();
		
		selectInstructorLinkQueryTermList.add(notDeleted);
		// logical operator set in previous query
		
		List<InstructorUserLink> instructorUserLinkList = instructorUserLinksDao.select(selectInstructorLinkColumnNameList, selectInstructorLinkQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		atro.setInstructorUserLinkList(instructorUserLinkList);
		
		return atro;
	}	
    
    @Transactional
    public EditUserReturnObject editUser(EditUserDto editUserDto) throws SQLException {
    		
    		EditUserReturnObject euro = new EditUserReturnObject(null, null, null, null);
    		
    		User editUser = usersDao.findById(editUserDto.getEditId());
    		
    		if (editUser != null)
    		{	
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
        		} 
    			else 
        		{
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
	    	    	    	
	    	    	    	// assumes the role exists
	    	    	    	usersRolesLinksDao.update(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID), newRoleId, updateRoleQueryTermList);
	        			
	        		// update instructorUserLinks table
	    	    	    	String updateColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID);
	    	    	    	Integer newInstructorId = editUserDto.getLinkedInstructorId();
	    	    	    	List<QueryTerm> updateInstructorQueryTermList = new ArrayList<>();
	    	    	    	
	    	    	    	QueryTerm updateInstructorIdTerm = new QueryTerm();
	    	    	    	updateInstructorIdTerm.setColumnName(updateColumnName);
	    	    	    	updateInstructorIdTerm.setComparisonOperator(ComparisonOperator.EQUAL);
	    	    	    	updateInstructorIdTerm.setValue(editUserDto.getEditId());
	    	    	    	updateInstructorQueryTermList.add(updateInstructorIdTerm);
	    	    	    	
	    	    	    if (	instructorUserLinksDao.update(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID), newInstructorId, updateInstructorQueryTermList) == 0) {
	    	    	    		// if no update occurred, do an insert
	    		    	    	InstructorUserLink link1 = new InstructorUserLink();
	    		    	    	link1.setInstructorId(editUserDto.getLinkedInstructorId());
	    		    	    	link1.setLinkedUserId(editUserDto.getEditId());
	    		    	    	
	    		    	    	List<String> insertColumnNameList = new ArrayList<>();
	    		    	    	List<String> keyHolderColumnNameList = new ArrayList<>();
	    		    	    	
	    		    	    	insertColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID));
	    		    	    	insertColumnNameList.add(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID));
	    		    	   	
	    		    	    	instructorUserLinksDao.insert(link1, insertColumnNameList, keyHolderColumnNameList);
	    	    	    }
        		}
    		}
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		// get all User_Roles_Links that aren't deleted
    		String selectUserRoleLinkColumnName = UsersRolesLink.getColumnName(UsersRolesLink.Columns.DELETED);
    		Boolean isNotDeleted = false;
    		
    		List<QueryTerm> selectUserRoleLinkQueryTermList = new ArrayList<>();
    		
    		QueryTerm selectUserRoleLinkTerm = new QueryTerm();
    		selectUserRoleLinkTerm.setColumnName(selectUserRoleLinkColumnName);
    		selectUserRoleLinkTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectUserRoleLinkTerm.setValue(isNotDeleted);
        		selectUserRoleLinkQueryTermList.add(selectUserRoleLinkTerm);
    		
        		List<String> selectUserRoleLinkColumnNameList = UsersRolesLink.getColumnNameList();
        		
    		List<UsersRolesLink> userRoleLinkList = usersRolesLinksDao.select(selectUserRoleLinkColumnNameList, selectUserRoleLinkQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
    		euro.setUserRoleLinkList(userRoleLinkList);
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		euro.setUserList(selectAllUsers());
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		// get a list of all instructors who aren't deleted
    		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
    		
    		List<String> selectInstructorColumnNameList = Instructor.getColumnNameList();
    		
    		QueryTerm notDeleted = notDeleted(Instructor.getColumnName(Instructor.Columns.DELETED));
    		notDeleted.setLogicalOperator(null);
    		
    		selectInstructorQueryTermList.add(notDeleted);
    		
    		List<Instructor> instructorList = instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
    		euro.setInstructorList(instructorList);
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		// get a list of all instructor_user_links that aren't deleted
    		List<QueryTerm> selectInstructorLinkQueryTermList = new ArrayList<>();
    		
    		List<String> selectInstructorLinkColumnNameList = InstructorUserLink.getColumnNameList();
    		
    		selectInstructorLinkQueryTermList.add(notDeleted);
    		// logical operator set in previous query
    		
    		List<InstructorUserLink> instructorUserLinkList = instructorUserLinksDao.select(selectInstructorLinkColumnNameList, selectInstructorLinkQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
    		euro.setInstructorUserLinkList(instructorUserLinkList);
    		
    		return euro;
    }
    
    private List<User> selectAllUsers() throws SQLException
    {
    		List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    		List<String> selectColumnNameList = User.getColumnNameList();
    		
    		String userSortColumnName = User.getColumnName(User.Columns.FIRST_NAME);
    		List<Pair<String, ColumnOrder>> userOrderByList = new ArrayList<>();
        	Pair<String, ColumnOrder> userOrderPair = new Pair<String, ColumnOrder>(userSortColumnName, ColumnOrder.ASC);
        	userOrderByList.add(userOrderPair);
        	
		return usersDao.select(selectColumnNameList, selectQueryTermList, userOrderByList);
    }
    
    private QueryTerm notDeleted(String columnName) {
		QueryTerm deletedQueryTerm = new QueryTerm();
		deletedQueryTerm.setColumnName(columnName);
		deletedQueryTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		deletedQueryTerm.setValue(true);
		deletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
		return deletedQueryTerm;
	}
}
