package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CalendarInfoDao;
import org.dselent.scheduling.server.dao.CourseSectionsDao;
import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CalendarInfo;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.returnobject.EditSectionReturnObject;
import org.dselent.scheduling.server.service.SectionsService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionsServiceImpl implements SectionsService {
	@Autowired
	private InstructorsDao instructorsDao;
	
	@Autowired
	private CoursesDao coursesDao;
	
	@Autowired
	private CourseSectionsDao sectionsDao;
	
	@Autowired
	private CalendarInfoDao calendarInfoDao;
	
	@Override
	public EditSectionReturnObject editSection(CourseSectionDto dto) throws SQLException {
		
		List<String> sectionInsertColumnNameList = new ArrayList<>();
    	List<String> sectionKeyHolderColumnNameList = new ArrayList<>();
    	List<String> calendarInsertColumnNameList = new ArrayList<>();
    	List<String> calendarKeyHolderColumnNameList = new ArrayList<>();
    	
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.POPULATION));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.SECTION_ID));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.CALENDAR_INFO_ID));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID));
    	sectionInsertColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.DELETED));
    	
    	sectionKeyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.ID));
    	sectionKeyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.CREATED_AT));
    	sectionKeyHolderColumnNameList.add(CourseSection.getColumnName(CourseSection.Columns.UPDATED_AT));
    	
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM));
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR));
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS));
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME));
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME));
    	calendarInsertColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED));
    	
    	calendarKeyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.ID));
    	calendarKeyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.CREATED_AT));
    	calendarKeyHolderColumnNameList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.UPDATED_AT));
    	
    	String message = "";
    	
    	CourseSection newSection = dto.getSection();
    	CalendarInfo newCal = dto.getCal();
    	
		if(dto.getSection().getId()==-1) {
			newCal.setDeleted(false);
			calendarInfoDao.insert(newCal, calendarInsertColumnNameList, calendarKeyHolderColumnNameList);
			
			System.out.println(newCal);
			
			newSection.setCalendarInfoId(newCal.getId());
			newSection.setCourseId(dto.getCourseId());
			newSection.setInstructorId(dto.getInstructorId());

			System.out.println(newSection);
			sectionsDao.insert(newSection, sectionInsertColumnNameList, sectionKeyHolderColumnNameList);
		} else {
			QueryTerm idTerm = new QueryTerm(CourseSection.getColumnName(CourseSection.Columns.ID), ComparisonOperator.EQUAL, dto.getSection().getId(), null);
			List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			queryTermList.add(idTerm);
			newSection = dto.getSection();
			if(newSection.getPopulation() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.POPULATION), newSection.getPopulation(), queryTermList);
			if(newSection.getSectionName() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.SECTION_NAME), newSection.getSectionName(), queryTermList);
			if(newSection.getSectionType() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.SECTION_TYPE), newSection.getSectionType(), queryTermList);
			if(newSection.getDeleted() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.DELETED), newSection.getDeleted(), queryTermList);
			
			QueryTerm calIdTerm = new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.ID), ComparisonOperator.EQUAL, dto.getSection().getCalendarInfoId(), null);
			List<QueryTerm> calQueryList = new ArrayList<QueryTerm>();
			calQueryList.add(calIdTerm);
			newCal = dto.getCal();
			if(newCal.getCalTerm() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM), newCal.getCalTerm(), calQueryList);
			if(newCal.getCalYear() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR), newCal.getCalYear(), calQueryList);
			if(newCal.getDays() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS), newCal.getDays(), calQueryList);
			if(newCal.getStartTime() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME), newCal.getStartTime(), calQueryList);
			if(newCal.getEndTime() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME), newCal.getEndTime(), calQueryList);
			if(newCal.getDeleted() != null)
				calendarInfoDao.update(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED), newCal.getDeleted(), calQueryList);

			if(dto.getCourseId() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID), dto.getCourseId(), queryTermList);
			if(dto.getInstructorId() != null)
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID), dto.getInstructorId(), queryTermList);
	
			newSection = sectionsDao.findById(newSection.getId());
		}	
		
		if(newSection.equals(null) == false && newCal.equals(null) == false) {
			message = "SUCCESS";
		}
		
		return new EditSectionReturnObject(message, newSection, newCal);
	}

}
