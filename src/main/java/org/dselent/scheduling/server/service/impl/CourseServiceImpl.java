package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.returnobject.SelectCourseReturnObject;
import org.dselent.scheduling.server.service.CourseService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	@Autowired
	private InstructorsDao instructorsDao;

	@Autowired
	private CoursesDao coursesDao;
	
	@Autowired
	private CustomDao customDao;

	@Autowired
	private CourseSectionsDao sectionsDao;
	
	public CourseServiceImpl() {
		//
	}
	
	

	@Override
	public SelectCourseReturnObject selectCourse(Course c, CalendarInfo ci) throws SQLException {
		
		
		String selectColumnName = CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME);
		Integer selectCourse = c.getId();
		/*
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
		
		QueryTerm selectCourseTerm = new QueryTerm();
		selectCourseTerm.setColumnName(selectColumnName);
		selectCourseTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCourseTerm.setValue(selectCourse);
		selectQueryTermList.add(selectCourseTerm);
		
		List<String> selectColumnNameList = CourseSection.getColumnNameList();
		
		List<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		orderByList.add(orderPair1);
		*/
		//Instructor by courseId
		/*
		String selectInstructor = CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID);
		String selectCourseColumn = CourseSection.getColumnName(CourseSection.Columns.COURSE_ID);
		
		List<QueryTerm> selectQueryTermList3 = new ArrayList<>();
				
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectCourseColumn);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectInstructorTerm.setValue(selectCourse);
		selectQueryTermList3.add(selectInstructorTerm);
		
		List<String> selectColumnNameList1 = Instructor.getColumnNameList();
				
		List<Pair<String, ColumnOrder>> orderByList3 = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair3 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		orderByList3.add(orderPair3);
		*/
		String selectCourseColumn = CourseSection.getColumnName(CourseSection.Columns.COURSE_ID);
		List<QueryTerm> selectQueryTermList3 = new ArrayList<>();
		
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectCourseColumn);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectInstructorTerm.setValue(selectCourse);
		selectQueryTermList3.add(selectInstructorTerm);
		
		List<String> selectColumnNameList1 = CourseSection.getColumnNameList();
		List<Pair<String, ColumnOrder>> orderByList3 = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair3 = new Pair<String, ColumnOrder>(selectColumnName, ColumnOrder.ASC);
		orderByList3.add(orderPair3);
		
		
		List<CourseSection> selectedCourseLists = sectionsDao.select(selectColumnNameList1, selectQueryTermList3, orderByList3);
		
		
		
		List<Integer> selectedInstructorID = new ArrayList<>();
		
		
		for (CourseSection cs: selectedCourseLists){
			selectedInstructorID.add(cs.getInstructorId());
		}
		
		List<Instructor> selectedInstructorList = new ArrayList<>();
		
		
		
		//CalendarInfo By year By Term
		
		
		String selectTerm = ci.getCalTerm();
		Integer selectYear = ci.getCalYear();
		
		List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
		
		QueryTerm selectCalendarInfoTerm = new QueryTerm();
		selectCalendarInfoTerm.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM));
		selectCalendarInfoTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCalendarInfoTerm.setValue(selectTerm);
		selectCalendarInfoTerm.setLogicalOperator(LogicalOperator.AND);
		selectCalendarInfoTerm.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR));
		selectCalendarInfoTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectCalendarInfoTerm.setValue(selectYear);
		selectQueryTermList2.add(selectCalendarInfoTerm);
		
		List<String> selectColumnNameList2 = CalendarInfo.getColumnNameList();
		
		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
		Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(CalendarInfo.getColumnName(CalendarInfo.Columns.ID), ColumnOrder.ASC);
		orderByList2.add(orderPair2);
		
		
		
		
		
		//
		
		
		try {
			@SuppressWarnings("unused")
			//List<CourseSection> selectedSectionList = sectionsDao.select(selectColumnNameList, selectQueryTermList, orderByList);
			List<CourseSection> selectedSectionList = customDao.getSectionsByCourse(selectCourse,selectYear,selectTerm);
			
			//List<Instructor> selectedInstructorList = sectionsDao.select(selectInstructor, selectQueryTermList3, orderByList3);
			for (Integer i : selectedInstructorID) {
				Instructor inst = instructorsDao.findById(i);
				selectedInstructorList.add(inst);
			}
			
			List<CalendarInfo> selectedCalendarInfoList = calendarInfoDao.select(selectColumnNameList2, selectQueryTermList2, orderByList2);
			
			return new SelectCourseReturnObject(selectedInstructorList, selectedSectionList, selectedCalendarInfoList);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Course editCourse(Course newCourse) throws SQLException {
		List<String> courseInsertColumnNameList = new ArrayList<>();
    	List<String> courseKeyholderColumnNameList = new ArrayList<>();
    	
    	courseInsertColumnNameList.add(Course.getColumnName(Course.Columns.COURSE_NAME));
    	courseInsertColumnNameList.add(Course.getColumnName(Course.Columns.COURSE_NUMBER));
    	courseInsertColumnNameList.add(Course.getColumnName(Course.Columns.FREQUENCY));
    	courseInsertColumnNameList.add(Course.getColumnName(Course.Columns.DELETED));
    	
    	courseKeyholderColumnNameList.add(Course.getColumnName(Course.Columns.ID));
    	courseKeyholderColumnNameList.add(Course.getColumnName(Course.Columns.CREATED_AT));
    	courseKeyholderColumnNameList.add(Course.getColumnName(Course.Columns.UPDATED_AT));
		if(newCourse.getId()==null) {
			coursesDao.insert(newCourse, courseInsertColumnNameList, courseKeyholderColumnNameList);
		} else {
			QueryTerm idTerm = new QueryTerm(Course.getColumnName(Course.Columns.ID), ComparisonOperator.EQUAL, newCourse.getId(), null);
			List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			queryTermList.add(idTerm);
			if(newCourse.getCourseName() != null)
				coursesDao.update(Course.getColumnName(Course.Columns.COURSE_NAME), newCourse.getCourseName(), queryTermList);
			if(newCourse.getCourseNumber() != null)
				coursesDao.update(Course.getColumnName(Course.Columns.COURSE_NUMBER), newCourse.getCourseNumber(), queryTermList);
			if(newCourse.getFrequency() != null)
				coursesDao.update(Course.getColumnName(Course.Columns.FREQUENCY), newCourse.getFrequency(), queryTermList);
			if(newCourse.getDeleted() != null)
				coursesDao.update(Course.getColumnName(Course.Columns.DELETED), newCourse.getDeleted(), queryTermList);
			newCourse = coursesDao.findById(newCourse.getId());
		}
		return newCourse;
	}

}
