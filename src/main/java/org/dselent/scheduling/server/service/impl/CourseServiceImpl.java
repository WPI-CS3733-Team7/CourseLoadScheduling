package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.UsersDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.returnobject.LoginUserReturnObject;
import org.dselent.scheduling.server.returnobject.SelectCourseReturnObject;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.dselent.scheduling.server.service.CourseService;
import org.dselent.scheduling.server.service.InstructorService;
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

public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseSectionsDao sectionsDao;
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	@Autowired
	private InstructorsDao instructorDao;
	
	public CourseServiceImpl() {
		//
	}
	
	

	@Override
	public SelectCourseReturnObject selectCourse(Integer courseId, String term, Integer year) {
		
		String selectColumnName = CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME);
		Integer selectCourse = courseId;
		
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
		
		QueryTerm selectCourseTerm = new QueryTerm();
		selectCourseTerm.setColumnName(selectColumnName);
		selectCourseTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCourseTerm.setValue(courseId);
		selectQueryTermList.add(selectCourseTerm);
		
		List<String> selectColumnNameList = CourseSection.getColumnNameList();
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		orderByList.add(orderPair1);
		
		//CalendarInfo By year By Term
		
		String selectColumnName2 = CalendarInfo.getColumnName(CalendarInfo.Columns.ID);
		//String selectColumnName3 = CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR);
		String selectTerm = term;
		Integer selectYear = year;
		
		List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
		
		QueryTerm selectCalendarInfoTerm = new QueryTerm();
		selectCalendarInfoTerm.setColumnName(selectColumnName2);
		selectCalendarInfoTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCalendarInfoTerm.setValue(selectTerm);
		selectCalendarInfoTerm.setLogicalOperator(LogicalOperator.AND);
		selectCalendarInfoTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCalendarInfoTerm.setValue(selectYear);
		selectQueryTermList2.add(selectCalendarInfoTerm);
		
		List<String> selectColumnNameList2 = CalendarInfo.getColumnNameList();
		
		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(selectColumnName2, ColumnOrder.ASC);
		orderByList2.add(orderPair2);
		
		//Instructor by courseId
		
		String selectColumnName3 = Instructor.getColumnName(Instructor.Columns.ID);
		
		//
		
		
		try {
			@SuppressWarnings("unused")
			List<CourseSection> selectedSectionList = sectionsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
			List<CalendarInfo> selectedCalendarInfoList = calendarInfoDao.select(selectColumnNameList2, selectQueryTermList2, orderByList2);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
