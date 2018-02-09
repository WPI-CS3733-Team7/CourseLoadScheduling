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
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.returnobject.LoginUserReturnObject;
import org.dselent.scheduling.server.service.UserService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
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
	public String registerUser(RegisterUserDto dto) throws SQLException
	{
		List<Integer> rowsAffectedList = new ArrayList<>();
		
		// check whether proposed username already exists in users table
		
		String selectColumnName = User.getColumnName(User.Columns.USER_NAME);
		String selectUserName = dto.getUserName();
    	
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
		QueryTerm selectUseNameTerm = new QueryTerm();
		selectUseNameTerm.setColumnName(selectColumnName);
		selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectUseNameTerm.setValue(selectUserName);
		selectQueryTermList.add(selectUseNameTerm);

		List<String> selectColumnNameList = User.getColumnNameList();
			
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<Pair<String, ColumnOrder>>();
		orderByList.add(new Pair<String, ColumnOrder>(User.getColumnName(User.Columns.ID), ColumnOrder.DESC));
    
		List<User> selectedUserList = usersDao.select(selectColumnNameList, selectQueryTermList, orderByList);
		
		if(!selectedUserList.isEmpty()) {
			return "ERROR: Username taken.";
		}
		
		// check whether password length is correct size
		
		int passwordLen = dto.getPassword().length();
		if (passwordLen < 8 || passwordLen > 20) {
			return "ERROR: Password must be between 8 and 20 characters.";
		}
		
		
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
		
		return "SUCCESS: User registered.";
	}
	
	//
    @Transactional
	@Override
	public LoginUserReturnObject loginUser(String userName, String password) throws SQLException
	{
		LoginUserReturnObject failureLuro = new LoginUserReturnObject("", null, null, null);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// select User entry from table with userName
		
		String selectColumnName = User.getColumnName(User.Columns.USER_NAME);
    		String selectUserName = userName;
    		System.out.println(userName);
    		List<QueryTerm> selectQueryTermList = new ArrayList<>();
    	
    		QueryTerm selectUseNameTerm = new QueryTerm();
    		selectUseNameTerm.setColumnName(selectColumnName);
    		selectUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectUseNameTerm.setValue(selectUserName);
    		selectQueryTermList.add(selectUseNameTerm);
    	
    		List<String> selectColumnNameList = User.getColumnNameList();
    		
    		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<Pair<String, ColumnOrder>>();
    		orderByList.add(new Pair<String, ColumnOrder>(User.getColumnName(User.Columns.ID), ColumnOrder.DESC));
    		
    		List<User> selectedUserList = usersDao.select(selectColumnNameList, selectQueryTermList, orderByList);

    		if (selectedUserList.isEmpty())
    		{
    			failureLuro.setMessage("ERROR: Username does not exist in database.");
    			return failureLuro;
    		}
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		User user = selectedUserList.get(0);

    		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    		if (passwordEncoder.matches(password + user.getSalt(), user.getEncryptedPassword()))
    		{
    			
    			// retrieve all instructors that are not deleted
    			
        		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
        		
        		String deleteColumnName = Instructor.getColumnName(Instructor.Columns.DELETED);
        		Boolean deleted = false;
        		
        		QueryTerm notDeletedTerm = new QueryTerm();
        		notDeletedTerm.setColumnName(deleteColumnName);
        		notDeletedTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        		notDeletedTerm.setValue(deleted);
        		selectInstructorQueryTermList.add(notDeletedTerm);
        	
        		List<String> selectInstructorColumnNameList = Instructor.getColumnNameList();
        		
        		String instructorSortColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
        		List<Pair<String, ColumnOrder>> instructorOrderByList = new ArrayList<>();
            	Pair<String, ColumnOrder> instructorOrderPair = new Pair<String, ColumnOrder>(instructorSortColumnName, ColumnOrder.ASC);
            	instructorOrderByList.add(instructorOrderPair);
        		
        		@SuppressWarnings("unused")
        		List<Instructor> instructorList = instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, instructorOrderByList);
    			
        		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
        		
        		// retrieve all courses
        		
        		String selectCourseColumnName = Course.getColumnName(Course.Columns.ID);
        		Integer selectCourseId = 0;
        	
        		List<QueryTerm> selectCourseQueryTermList = new ArrayList<>();
        	
        		notDeletedTerm.setColumnName(Course.getColumnName(Course.Columns.DELETED));
        		selectCourseQueryTermList.add(notDeletedTerm);
        		
        		List<String> selectCourseColumnNameList = Course.getColumnNameList();
        		
        		String courseSortColumnName = Course.getColumnName(Course.Columns.COURSE_NUMBER);
        		List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<>();
            	Pair<String, ColumnOrder> courseOrderPair = new Pair<String, ColumnOrder>(courseSortColumnName, ColumnOrder.ASC);
            	courseOrderByList.add(courseOrderPair);
        		
        		List<Course> courseList = coursesDao.select(selectCourseColumnNameList, selectCourseQueryTermList, courseOrderByList);
 
        		// return message, userId, and lists of instructors and courses
        		
        		return new LoginUserReturnObject("SUCCESS", user.getId(), instructorList, courseList);
    			
    		} else {
    			
    			// passwords didn't match
    			failureLuro.setMessage("ERROR: Incorrect password.");
    			return failureLuro;
    		}
	}   

}
