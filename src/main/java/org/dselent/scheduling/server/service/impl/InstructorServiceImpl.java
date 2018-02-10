package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.dselent.scheduling.server.service.InstructorService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InstructorServiceImpl implements InstructorService
{
	
	@Autowired
	private InstructorsDao instructorsDao;
	
	@Autowired
	private CourseSectionsDao sectionsDao;
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	@Autowired
	private CustomDao customDao;
	
    public InstructorServiceImpl()
    {
    	//
    }
    
    @Override
	public SelectInstructorReturnObject selectInstructor(Instructor i, CalendarInfo Ci) throws SQLException {
    		
		
		Integer selectInstructor = i.getId();
		Integer selectYear = Ci.getCalYear();
		String selectTerm = Ci.getCalTerm();
		
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
    		QueryTerm selectCalendarInfoTerm3 = new QueryTerm();
    		selectCalendarInfoTerm3.setLogicalOperator(LogicalOperator.AND);
    		selectCalendarInfoTerm3.setColumnName(CalendarInfo.getColumnName(CalendarInfo.Columns.ID));
    		selectCalendarInfoTerm3.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectCalendarInfoTerm3.setValue(calendarInfoId);
    		selectQueryTermList2.add(selectCalendarInfoTerm2);
    		
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

	@Override
	public List<Instructor> editInstructor(Instructor in) throws SQLException {
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
			in = instructorsDao.findById(in.getId());
		}
		
		// Return a list of all undeleted instructors
		String selectInstructorColumnName = Instructor.getColumnName(Instructor.Columns.ID);
		Integer selectInstructorId = 0;
	
		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
	
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectInstructorColumnName);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectInstructorTerm.setValue(selectInstructorId);
		selectInstructorQueryTermList.add(selectInstructorTerm);
		
		String deleteColumnName = Instructor.getColumnName(Instructor.Columns.DELETED);
		selectInstructorQueryTermList.add(notDeleted(deleteColumnName));
	
		List<String> selectInstructorColumnNameList = Instructor.getColumnNameList();
		
		String instructorSortColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
		List<Pair<String, ColumnOrder>> instructorOrderByList = new ArrayList<>();
		Pair<String, ColumnOrder> instructorOrderPair = new Pair<String, ColumnOrder>(instructorSortColumnName, ColumnOrder.ASC);
		instructorOrderByList.add(instructorOrderPair);
		
		return instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, instructorOrderByList);
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
