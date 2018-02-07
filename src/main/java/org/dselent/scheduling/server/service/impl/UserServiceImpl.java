package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.returnobject.LoginUserReturnObject;
import org.dselent.scheduling.server.service.UserService;
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
public class UserServiceImpl implements UserService
{
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private UsersRolesLinksDao usersRolesLinksDao;
	
	@Autowired
	private CoursesDao coursesDao;
	
	@Autowired
	private InstructorsDao instructorsDao;
	
    public UserServiceImpl()
    {
    	//
    }
    
    /*
     * (non-Javadoc)
     * @see org.dselent.scheduling.server.service.UserService#registerUser(org.dselent.scheduling.server.dto.RegisterUserDto)
     */
    @Transactional
    @Override
	public List<Integer> registerUser(RegisterUserDto dto) throws SQLException
	{
		List<Integer> rowsAffectedList = new ArrayList<>();
		
		// TODO validate business constraints
			// ^^students should do this^^
			// password strength requirements
			// email requirements
			// null values
			// etc...
		
		String salt = KeyGenerators.string().generateKey();
		String saltedPassword = dto.getPassword() + salt;
		PasswordEncoder passwordEncorder = new BCryptPasswordEncoder();
		String encryptedPassword = passwordEncorder.encode(saltedPassword);
		
		User user = new User();
		user.setUserName(dto.getUserName());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setEncryptedPassword(encryptedPassword);
		user.setSalt(salt);
    	user.setUserStateId(1);
    	
    	List<String> userInsertColumnNameList = new ArrayList<>();
    	List<String> userKeyHolderColumnNameList = new ArrayList<>();
    	
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.USER_NAME));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.FIRST_NAME));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.LAST_NAME));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.EMAIL));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.ENCRYPTED_PASSWORD));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.SALT));
    	userInsertColumnNameList.add(User.getColumnName(User.Columns.USER_STATE_ID));
    	
    	userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.ID));
    	userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.CREATED_AT));
    	userKeyHolderColumnNameList.add(User.getColumnName(User.Columns.UPDATED_AT));
		
    	rowsAffectedList.add(usersDao.insert(user, userInsertColumnNameList, userKeyHolderColumnNameList));

		//
     	
    	// for now, assume users can only register with default role id
    	// may change in the future
    	
		UsersRolesLink usersRolesLink = new UsersRolesLink();
		usersRolesLink.setUserId(user.getId());
		usersRolesLink.setRoleId(1); // hard coded as regular user
    	
    	List<String> usersRolesLinksInsertColumnNameList = new ArrayList<>();
    	List<String> usersRolesLinksKeyHolderColumnNameList = new ArrayList<>();
    	
    	usersRolesLinksInsertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.USER_ID));
    	usersRolesLinksInsertColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ROLE_ID));
    	
    	usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.ID));
    	usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.CREATED_AT));
    	usersRolesLinksKeyHolderColumnNameList.add(UsersRolesLink.getColumnName(UsersRolesLink.Columns.DELETED));
		
    	rowsAffectedList.add(usersRolesLinksDao.insert(usersRolesLink, usersRolesLinksInsertColumnNameList, usersRolesLinksKeyHolderColumnNameList));
		
		return rowsAffectedList;
	}
	
	//

	@Override
	public LoginUserReturnObject loginUser(String userName, String password)
	{
		// TODO Auto-generated method stub
		
		String selectColumnName = User.getColumnName(User.Columns.USER_NAME);
    	String selectUserName = userName;
    	
    	List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    	QueryTerm selectUseNameTerm = new QueryTerm();
    	selectUseNameTerm.setColumnName(selectColumnName);
    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	selectUseNameTerm.setValue(selectUserName);
    	selectQueryTermList.add(selectUseNameTerm);
    	
    	List<String> selectColumnNameList = User.getColumnNameList();
    	
    	List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    	orderByList.add(orderPair1);
    	
    	LoginUserReturnObject failureLuro = new LoginUserReturnObject(false, null, null, null);

		try {
			@SuppressWarnings("unused")
			List<User> selectedUserList = usersDao.select(selectColumnNameList, selectQueryTermList, orderByList);
			
			if (selectedUserList.size() == 0) 
				return failureLuro;
			
			User user = selectedUserList.get(0);
			
			if(user.getEncryptedPassword().equals(password)) {
				
				// how do we select all?
				selectUseNameTerm = new QueryTerm();
		    	selectUseNameTerm.setColumnName("*");
		    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		    	selectUseNameTerm.setValue("*");
		    	selectQueryTermList.add(selectUseNameTerm);
		    	
		    	selectColumnNameList = Course.getColumnNameList();
		    	
		    	orderByList = new ArrayList<>();
		    	orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		    	orderByList.add(orderPair1);
				
				List<Course> courseList = coursesDao.select(selectColumnNameList, selectQueryTermList, orderByList);
				
				selectUseNameTerm = new QueryTerm();
		    	selectUseNameTerm.setColumnName("*");
		    	selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		    	selectUseNameTerm.setValue("*");
		    	selectQueryTermList.add(selectUseNameTerm);
		    	
		    	selectColumnNameList = Course.getColumnNameList();
		    	
		    	orderByList = new ArrayList<>();
		    	orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		    	orderByList.add(orderPair1);
		    	
				List<Instructor> instructorList = instructorsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
				
				Boolean success = true;
				Integer userId = user.getId();
				
				return new LoginUserReturnObject(success, userId, instructorList, courseList);
				
				
			} else {
				return failureLuro;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//usersDao.select(selectColumnNameList, queryTermList, orderByList);
		
		return failureLuro;
	}   

}
