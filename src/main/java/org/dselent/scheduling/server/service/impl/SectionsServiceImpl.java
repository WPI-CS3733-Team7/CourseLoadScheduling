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
    	
    	CourseSection newSection = new CourseSection();
    	
    	CalendarInfo newCal = new CalendarInfo();
    	
		if(dto.getSection().getId()==null) {
			calendarInfoDao.insert(dto.getCal(), calendarInsertColumnNameList, calendarKeyHolderColumnNameList);
			
			List<QueryTerm> queryTermList = new ArrayList<QueryTerm>();
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_TERM), ComparisonOperator.EQUAL, dto.getCal().getCalTerm(), null));
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.CAL_YEAR), ComparisonOperator.EQUAL, dto.getCal().getCalYear(), LogicalOperator.AND));
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.DAYS), ComparisonOperator.EQUAL, dto.getCal().getDays(), LogicalOperator.AND));
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.START_TIME), ComparisonOperator.EQUAL, dto.getCal().getStartTime(), LogicalOperator.AND));
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.END_TIME), ComparisonOperator.EQUAL, dto.getCal().getEndTime(), LogicalOperator.AND));
			queryTermList.add(new QueryTerm(CalendarInfo.getColumnName(CalendarInfo.Columns.DELETED), ComparisonOperator.EQUAL, dto.getCal().getDeleted(), LogicalOperator.AND));
			
			List<Pair<String, ColumnOrder>> orderByList = new ArrayList<Pair<String, ColumnOrder>>();
			orderByList.add(new Pair(CalendarInfo.getColumnName(CalendarInfo.Columns.ID), ColumnOrder.DESC));
			
			List<String> columnSelectList = new ArrayList<String>();
			columnSelectList.add(CalendarInfo.getColumnName(CalendarInfo.Columns.ID));
			
			List<CalendarInfo> resultsList = calendarInfoDao.select(columnSelectList, queryTermList, orderByList);
			CalendarInfo calCreated = resultsList.get(0);
			newSection = dto.getSection();
			newSection.setCalendarInfoId(calCreated.getId());
			
			List<QueryTerm> courseQueryList = new ArrayList<QueryTerm>();
			courseQueryList.add(new QueryTerm(Course.getColumnName(Course.Columns.COURSE_NAME), ComparisonOperator.EQUAL, dto.getCourseName(), null));
			
			List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<Pair<String, ColumnOrder>>();
			courseOrderByList.add(new Pair(Course.getColumnName(Course.Columns.ID), ColumnOrder.ASC));
			
			List<String> courseColumnSelectList = new ArrayList<String>();
			courseColumnSelectList.add(Course.getColumnName(Course.Columns.ID));
			
			List<Course> courseResultsList = coursesDao.select(courseColumnSelectList, courseQueryList, courseOrderByList);
			Course linkedCourse = courseResultsList.get(0);
			
			newSection.setCourseId(linkedCourse.getId());
			
			List<QueryTerm> instQueryList = new ArrayList<QueryTerm>();
			instQueryList.add(new QueryTerm(Instructor.getColumnName(Instructor.Columns.FIRST_NAME), ComparisonOperator.EQUAL, dto.getInstFirstName(), null));
			instQueryList.add(new QueryTerm(Instructor.getColumnName(Instructor.Columns.LAST_NAME), ComparisonOperator.EQUAL, dto.getInstLastName(), null));
			
			List<Pair<String, ColumnOrder>> instOrderByList = new ArrayList<Pair<String, ColumnOrder>>();
			instOrderByList.add(new Pair(Instructor.getColumnName(Instructor.Columns.ID), ColumnOrder.ASC));
			
			List<String> instColumnSelectList = new ArrayList<String>();
			instColumnSelectList.add(Instructor.getColumnName(Instructor.Columns.ID));
			
			List<Instructor> instResultsList = instructorsDao.select(instColumnSelectList, instQueryList, instOrderByList);
			Instructor linkedInstructor = instResultsList.get(0);
			
			newSection.setInstructorId(linkedInstructor.getId());
			
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

			if(dto.getCourseName() != "") {
				List<QueryTerm> courseQueryList = new ArrayList<QueryTerm>();
				courseQueryList.add(new QueryTerm(Course.getColumnName(Course.Columns.COURSE_NAME), ComparisonOperator.EQUAL, dto.getCourseName(), null));
				
				List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<Pair<String, ColumnOrder>>();
				courseOrderByList.add(new Pair<String, ColumnOrder>(Course.getColumnName(Course.Columns.ID), ColumnOrder.ASC));
				
				List<String> courseColumnSelectList = new ArrayList<String>();
				courseColumnSelectList.add(Course.getColumnName(Course.Columns.ID));
				
				List<Course> courseResultsList = coursesDao.select(courseColumnSelectList, courseQueryList, courseOrderByList);
				Course linkedCourse = courseResultsList.get(0);
				
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.COURSE_ID), linkedCourse.getId(), queryTermList);
			}
			
			if(dto.getInstFirstName() != "" || dto.getInstLastName() != "") {
				List<QueryTerm> instQueryList = new ArrayList<QueryTerm>();
				instQueryList.add(new QueryTerm(Instructor.getColumnName(Instructor.Columns.FIRST_NAME), ComparisonOperator.EQUAL, dto.getInstFirstName(), null));
				instQueryList.add(new QueryTerm(Instructor.getColumnName(Instructor.Columns.LAST_NAME), ComparisonOperator.EQUAL, dto.getInstLastName(), null));
				
				List<Pair<String, ColumnOrder>> instOrderByList = new ArrayList<Pair<String, ColumnOrder>>();
				instOrderByList.add(new Pair<String, ColumnOrder>(Instructor.getColumnName(Instructor.Columns.ID), ColumnOrder.ASC));
				
				List<String> instColumnSelectList = new ArrayList<String>();
				instColumnSelectList.add(Instructor.getColumnName(Instructor.Columns.ID));
				
				List<Instructor> instResultsList = instructorsDao.select(instColumnSelectList, instQueryList, instOrderByList);
				Instructor linkedInstructor = instResultsList.get(0);
				
				sectionsDao.update(CourseSection.getColumnName(CourseSection.Columns.INSTRUCTOR_ID), linkedInstructor.getId(), queryTermList);
			}
	
			newSection = sectionsDao.findById(newSection.getId());
			
			if(newSection.equals(null) == false && newCal.equals(null) == false) {
				message = "Success!";
			}
		}	
		
		return new EditSectionReturnObject(message, newSection, newCal);
	}

}
