package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dao.CourseLoadsDao;
import org.dselent.scheduling.server.dao.CourseLoadsHistoryDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.InstructorsHistoryDao;
import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.CourseLoadHistory;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorHistory;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.EditInstructorReturnObject;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.dselent.scheduling.server.service.InstructorService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
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
	private CourseLoadsDao courseLoadsDao;
	
	@Autowired
	private InstructorsHistoryDao instructorsHistoryDao;
	
	@Autowired
	private CourseLoadsHistoryDao courseLoadsHistoryDao;
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	@Autowired
	private CustomDao customDao;
	
    public InstructorServiceImpl()
    {
    	//
    }
    
    @Override
	public SelectInstructorReturnObject selectInstructor(Integer selectInstructor, Integer selectYear, String selectTerm) throws SQLException {
		
		// select all the sections with Instructor ID
		List<CourseSection> selectedSectionList = customDao.getSectionsByInstructor(selectInstructor,selectYear,selectTerm);
		
		/*
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
    		*/
    		
    		//CalendarInfo 
		List<Integer> calendarInfobyInstructor = new ArrayList<>();
		
		for(CourseSection cs : selectedSectionList) {
			calendarInfobyInstructor.add(cs.getCalendarInfoId());
		}
    		
		List<CalendarInfo> selectedCalendarInfoList = new ArrayList<>();
		
		for (Integer calendarInfoId : calendarInfobyInstructor) {
		
    		String selectColumnName2 = CalendarInfo.getColumnName(CalendarInfo.Columns.ID);
    		
    		List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
    		
    		QueryTerm selectCalendarInfoTerm = new QueryTerm();
    		selectCalendarInfoTerm.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM));
    		selectCalendarInfoTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectCalendarInfoTerm.setValue(selectTerm);
    		selectQueryTermList2.add(selectCalendarInfoTerm);
    		QueryTerm selectCalendarInfoTerm2 = new QueryTerm();
    		selectCalendarInfoTerm2.setLogicalOperator(LogicalOperator.AND);
    		selectCalendarInfoTerm2.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR));
    		selectCalendarInfoTerm2.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectCalendarInfoTerm2.setValue(selectYear);
    		selectQueryTermList2.add(selectCalendarInfoTerm2);
    		QueryTerm selectCalendarInfoTerm3 = new QueryTerm();
    		selectCalendarInfoTerm3.setLogicalOperator(LogicalOperator.AND);
    		selectCalendarInfoTerm3.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.ID));
    		selectCalendarInfoTerm3.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectCalendarInfoTerm3.setValue(calendarInfoId);
    		selectQueryTermList2.add(selectCalendarInfoTerm3);
    		
    		String calendarDeletedColumnName = CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED);
    		selectQueryTermList2.add(notDeleted(calendarDeletedColumnName));
    		
    		
    		List<String> selectColumnNameList2 = CalendarInfo.getColumnNameList();
        	
    		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
    		Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(selectColumnName2, ColumnOrder.ASC);
    		orderByList2.add(orderPair2);
    		
    		 selectedCalendarInfoList.addAll(calendarInfoDao.select(selectColumnNameList2, selectQueryTermList2, orderByList2));
    		
		}
    		
		return new SelectInstructorReturnObject(selectedSectionList, selectedCalendarInfoList);
	}

    @Transactional
	@Override
	public EditInstructorReturnObject editInstructor(Instructor in, CourseLoad cl) throws SQLException
    {	
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
	    	
	    	List<String> courseLoadInsertColumnNameList = new ArrayList<>();
	    	List<String> courseLoadKeyHolderColumnNameList = new ArrayList<>();
	    	
	    courseLoadInsertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE));
	    courseLoadInsertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_DESCRIPTION));
	    courseLoadInsertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.INSTRUCTOR_ID));
	    courseLoadInsertColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.DELETED));
	    
	 	courseLoadKeyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.ID));
	 	courseLoadKeyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.CREATED_AT));
	 	courseLoadKeyHolderColumnNameList.add(CourseLoad.getColumnName(CourseLoad.Columns.UPDATED_AT));
	    		
	 	QueryTerm idTerm = new QueryTerm(Instructor.getColumnName(Instructor.Columns.ID), ComparisonOperator.EQUAL, in.getId(), null);
		List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
		queryTermList.add(idTerm);
		
		QueryTerm idhTerm = new QueryTerm(InstructorHistory.getColumnName(InstructorHistory.Columns.FORMER_ID), ComparisonOperator.EQUAL, in.getId(), null);
		List<QueryTerm> iQueryTermList = new ArrayList<QueryTerm>();
		iQueryTermList.add(idhTerm);
		
		QueryTerm clTerm = new QueryTerm(CourseLoad.getColumnName(CourseLoad.Columns.ID), ComparisonOperator.EQUAL, cl.getId(), null);
		List<QueryTerm> clQueryTermList = new ArrayList<QueryTerm>();
		clQueryTermList.add(clTerm);
		
		QueryTerm clhQueryTerm = new QueryTerm(CourseLoadHistory.getColumnName(CourseLoadHistory.Columns.FORMER_ID), ComparisonOperator.EQUAL, cl.getId(), null);
	    List<QueryTerm> clhQueryTermList = new ArrayList<QueryTerm>();
	    clhQueryTermList.add(clhQueryTerm);
		
		// first check if need to delete
	    if (in.getId()>0 && in.getDeleted() == true)
	    {	
	    		courseLoadsHistoryDao.delete(clhQueryTermList);
	    		courseLoadsDao.delete(clQueryTermList);
	    		instructorsHistoryDao.delete(iQueryTermList);
	    		instructorsDao.delete(queryTermList);
	    		
	    		in.setDeleted(true);
	    		cl.setId(-1);
	    		cl.setDeleted(true);
	    		return new EditInstructorReturnObject(in, cl);
	    }
	    else if(in.getId()==null || in.getId()<0)
		{
			// creating new instructor and new course load entry
			instructorsDao.insert(in, instructorInsertColumnNameList, instructorKeyHolderColumnNameList);
			cl.setInstructorId(in.getId());
			cl.setDeleted(false);
			courseLoadsDao.insert(cl, courseLoadInsertColumnNameList, courseLoadKeyHolderColumnNameList);
			return new EditInstructorReturnObject(in, cl);
		} 
		else
		{
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
			
			Instructor editedInstructor = instructorsDao.findById(in.getId());
			
			System.out.println(cl);
			
			if(cl.getInstructorId() != null)
				courseLoadsDao.update(CourseLoad.getColumnName(CourseLoad.Columns.INSTRUCTOR_ID), cl.getInstructorId(), clQueryTermList);
			if(cl.getLoadType() != null)
				courseLoadsDao.update(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_TYPE), cl.getLoadType(), clQueryTermList);
			if(cl.getLoadDescription() != null)
				courseLoadsDao.update(CourseLoad.getColumnName(CourseLoad.Columns.LOAD_DESCRIPTION), cl.getLoadDescription(), clQueryTermList);
			
			CourseLoad editedCourseLoad = courseLoadsDao.findById(cl.getId());
			
			return new EditInstructorReturnObject(editedInstructor, editedCourseLoad);
		}
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
