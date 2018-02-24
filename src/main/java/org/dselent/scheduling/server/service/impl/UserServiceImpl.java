package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseLoadsDao;
import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UserRole;
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
	
	@Autowired
	private UserRolesDao usersRolesDao;
	
	@Autowired
	private CourseLoadsDao courseLoadsDao;
	
	@Autowired
	private InstructorUserLinksDao instructorUserLinksDao;
	
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
    		LoginUserReturnObject luro = new LoginUserReturnObject("", -1, "", -1, null, null, null);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// select User entry from table with userName
		
		String selectColumnName = User.getColumnName(User.Columns.USER_NAME);
    		String selectUserName = userName;
    		
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
    			luro.setMessage("ERROR: Username does not exist in database.");
    			return luro;
    		}
    		
    		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
    		
    		User user = selectedUserList.get(0);

    		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    		if (passwordEncoder.matches(password + user.getSalt(), user.getEncryptedPassword()))
    		{
    			// logical checks passed, now begin filling return object with necessary data
    			luro.setMessage("SUCCESS: Login authorized.");
    			luro.setUserId(user.getId());
    			
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
    			luro.setInstructorList(instructorList);
    			
        		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
        		
        		// retrieve all courses that are not deleted
        		
        		List<QueryTerm> selectCourseQueryTermList = new ArrayList<>();
        	
        		notDeletedTerm.setColumnName(Course.getColumnName(Course.Columns.DELETED));
        		selectCourseQueryTermList.add(notDeletedTerm);
        		
        		List<String> selectCourseColumnNameList = Course.getColumnNameList();
        		
        		String courseSortColumnName = Course.getColumnName(Course.Columns.COURSE_NUMBER);
        		List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<>();
            	Pair<String, ColumnOrder> courseOrderPair = new Pair<String, ColumnOrder>(courseSortColumnName, ColumnOrder.ASC);
            	courseOrderByList.add(courseOrderPair);
        		
        		List<Course> courseList = coursesDao.select(selectCourseColumnNameList, selectCourseQueryTermList, courseOrderByList);
 
        		luro.setCourseList(courseList);
        		
        		// select all course load entries that are not empty
        		
        		List<QueryTerm> selectCourseLoadQueryTermList = new ArrayList<>();
        		notDeletedTerm.setColumnName(CourseLoad.getColumnName(CourseLoad.Columns.DELETED));
        		selectCourseLoadQueryTermList.add(notDeletedTerm);
        		
        		List<String> selectCourseLoadColumnNameList = CourseLoad.getColumnNameList();
        		
        		List<CourseLoad> courseLoadList = courseLoadsDao.select(selectCourseLoadColumnNameList, selectCourseLoadQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
        		
        		luro.setCourseLoadList(courseLoadList);
        		
        		// GETTING USER'S ROLE
        		
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
        		
        		// search for one that matches user's id
        		int userRoleId = -1;
        		for (int i = 0; i < userRoleLinkList.size(); i++) {
        			if (userRoleLinkList.get(i).getUserId() == user.getId()) {
        				userRoleId = userRoleLinkList.get(i).getRoleId();
        			}
        		}
        		
        		// get role Id of that entry, and match to a string from the user role table
        		String selectRoleColumnName = UserRole.getColumnName(UserRole.Columns.DELETED);
        		
        		List<QueryTerm> selectRoleQueryTermList = new ArrayList<>();
        		
        		QueryTerm selectRoleTerm = new QueryTerm();
        		selectRoleTerm.setColumnName(selectRoleColumnName);
        		selectRoleTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        		selectRoleTerm.setValue(isNotDeleted);
        		selectRoleQueryTermList.add(selectRoleTerm);
        		
            	List<String> selectRoleColumnNameList = UserRole.getColumnNameList();
            		
        		List<UserRole> userRoles = usersRolesDao.select(selectRoleColumnNameList, selectRoleQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
        		
        		String userRoleName = "";
        		for (int i = 0; i < userRoles.size(); i++) {
        			if(userRoles.get(i).getId() == userRoleId) {
        				userRoleName = userRoles.get(i).getRoleName();
        			}
        		}
        		luro.setUserRole(userRoleName);
        		
        		// GET USER'S LINKED INSTRUCTOR ID
        		
        		// first get list of instructor user Links
        		String selectIULColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID);
        		Integer selectIULUserId = user.getId();
        		
        		List<QueryTerm> selectIULQueryTermList = new ArrayList<>();
        		
        		QueryTerm selectIULUserIdQueryTerm = new QueryTerm();
        		selectIULUserIdQueryTerm.setColumnName(selectIULColumnName);
        		selectIULUserIdQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        		selectIULUserIdQueryTerm.setValue(selectIULUserId);
        		selectIULQueryTermList.add(selectIULUserIdQueryTerm);
        		
        		QueryTerm selectIULNotDeletedQueryTerm = new QueryTerm();
        		selectIULNotDeletedQueryTerm.setColumnName(InstructorUserLink.getColumnName(InstructorUserLink.Columns.DELETED));
        		selectIULNotDeletedQueryTerm.setComparisonOperator(ComparisonOperator.EQUAL);
        		selectIULNotDeletedQueryTerm.setValue(false);
        		selectIULNotDeletedQueryTerm.setLogicalOperator(LogicalOperator.AND);
        		selectIULQueryTermList.add(selectIULNotDeletedQueryTerm);
        		
        		List<String> selectIULColumnNameList = InstructorUserLink.getColumnNameList();
        		
        		List<InstructorUserLink> instructorUserLinkList = instructorUserLinksDao.select(selectIULColumnNameList, selectIULQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
        		
        		// if user is linked to an instructor, change the instructor id in the return object
        		if (!instructorUserLinkList.isEmpty()) {
        			luro.setLinkedInstructorId(instructorUserLinkList.get(0).getInstructorId());
        		}
        		
        		// return LoginUserReturnObject
        		
        		return luro;	
    		}
    		else
    		{	
    			// passwords didn't match
    			luro.setMessage("ERROR: Incorrect password for given username.");
    			return luro;
    		}
	}   

}
