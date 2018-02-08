package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.returnobject.LoginUserReturnObject;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.dselent.scheduling.server.service.InstructorService;
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
public class InstructorServiceImpl implements InstructorService
{
	
	@Autowired
	private InstructorsDao instructorsDao;
	
	@Autowired
	private CourseSectionsDao sectionsDao;
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	
    public InstructorServiceImpl()
    {
    	//
    }
    
    @Override
	public SelectInstructorReturnObject selectInstructor(Integer instructorId) {
		// TODO Auto-generated method stub
		
		// CourseSection
		String selectColumnName = CourseSection.getColumnName(CourseSection.Columns.ID);
		Integer selectInstructor = instructorId;
		
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
		
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectColumnName);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectInstructorTerm.setValue(selectInstructor);
		selectQueryTermList.add(selectInstructorTerm);
		
		List<String> selectColumnNameList = CourseSection.getColumnNameList();
    	
    		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    		Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
    		orderByList.add(orderPair1);
    		
    		//CalendarInfo 
    		
    		String selectColumnName2 = CalendarInfo.getColumnName(CalendarInfo.Columns.ID);
    		
    		List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
    		
    		QueryTerm selectCalendarInfoTerm = new QueryTerm();
    		selectCalendarInfoTerm.setColumnName("*");
    		selectQueryTermList2.add(selectCalendarInfoTerm);
    		
    		List<String> selectColumnNameList2 = CalendarInfo.getColumnNameList();
        	
    		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
    		Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(selectColumnName2, ColumnOrder.ASC);
    		orderByList2.add(orderPair2);
    		
    		try {
    			
    		
    			@SuppressWarnings("unused")
    			List<CourseSection> selectedSectionList = sectionsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
    			List<CalendarInfo> selectedCalendarInfoList = calendarInfoDao.select(selectColumnNameList2, selectQueryTermList2, orderByList2);
    			
    			return new SelectInstructorReturnObject(selectedSectionList, selectedCalendarInfoList);
    		}
    		catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return null;
	}

	@Override
	public Instructor editInstructor(Instructor in) throws SQLException {
		List<String> instructorInsertColumnNameList = new ArrayList<>();
    	List<String> instructorKeyHolderColumnNameList = new ArrayList<>();
    	
    	instructorInsertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.RANK));
    	instructorInsertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.FIRST_NAME));
    	instructorInsertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.LAST_NAME));
    	instructorInsertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.EMAIL));
    	instructorInsertColumnNameList.add(Instructor.getColumnName(Instructor.Columns.DELETED));
    	
    	instructorKeyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.ID));
    	instructorKeyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.CREATED_AT));
    	instructorKeyHolderColumnNameList.add(Instructor.getColumnName(Instructor.Columns.UPDATED_AT));
		if(in.getId()==null) {
			instructorsDao.insert(in, instructorInsertColumnNameList, instructorKeyHolderColumnNameList);
		} else {
			QueryTerm idTerm = new QueryTerm(Instructor.getColumnName(Instructor.Columns.ID), ComparisonOperator.EQUAL, in.getId(), null);
			List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			queryTermList.add(idTerm);
			if(in.getRank() != null)
				instructorsDao.update(Instructor.getColumnName(Instructor.Columns.RANK), in.getRank(), queryTermList);
			if(in.getFirstName() != null)
				instructorsDao.update(Instructor.getColumnName(Instructor.Columns.FIRST_NAME), in.getFirstName(), queryTermList);
			if(in.getLastName() != null)
				instructorsDao.update(Instructor.getColumnName(Instructor.Columns.LAST_NAME), in.getLastName(), queryTermList);
			if(in.getEmail() != null)
				instructorsDao.update(Instructor.getColumnName(Instructor.Columns.EMAIL), in.getEmail(), queryTermList);
			if(in.getDeleted() != null)
				instructorsDao.update(Instructor.getColumnName(Instructor.Columns.DELETED), in.getDeleted(), queryTermList);
		}
		return null;
	}
    
       

}
