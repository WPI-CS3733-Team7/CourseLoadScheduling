package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.CourseLoadsDao;
import org.dselent.scheduling.server.dao.CoursesDao;
import org.dselent.scheduling.server.dao.CustomDao;
import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.CourseLoad;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.SchedulerTabReturnObject;
import org.dselent.scheduling.server.returnobject.ValidateReturnObject;
import org.dselent.scheduling.server.service.SchedulerScheduleService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.LogicalOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerScheduleServiceImpl implements SchedulerScheduleService 
{
	@Autowired
	private InstructorsDao instructorsDao;
	
	@Autowired
	private CourseLoadsDao courseLoadsDao;
	
	@Autowired
	private CoursesDao coursesDao;
	
	@Autowired
	private CustomDao customDao;
	
	@Autowired
	private InstructorUserLinksDao instructorUserLinksDao;
	
	@Override
	public SchedulerTabReturnObject page(Integer userId) throws SQLException {
		
		// select all instructors
		String selectInstructorColumnName = Instructor.getColumnName(Instructor.Columns.ID);
		Integer selectInstructorId = 0;
	
		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
	
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectInstructorColumnName);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectInstructorTerm.setValue(selectInstructorId);
		selectInstructorQueryTermList.add(selectInstructorTerm);
		
		String instructorDeletedColumnName = Instructor.getColumnName(Instructor.Columns.DELETED);
		selectInstructorQueryTermList.add(notDeleted(instructorDeletedColumnName));
	
		List<String> selectInstructorColumnNameList = Instructor.getColumnNameList();
		
		String instructorSortColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
		List<Pair<String, ColumnOrder>> instructorOrderByList = new ArrayList<>();
		Pair<String, ColumnOrder> instructorOrderPair = new Pair<String, ColumnOrder>(instructorSortColumnName, ColumnOrder.ASC);
		instructorOrderByList.add(instructorOrderPair);

		List<Instructor> instructorList = instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, instructorOrderByList);
		
		// select all courses
		String selectCourseColumnName = Course.getColumnName(Course.Columns.ID);
		Integer selectCourseId = 0;
	
		List<QueryTerm> selectCourseQueryTermList = new ArrayList<>();
	
		QueryTerm selectCourseTerm = new QueryTerm();
		selectCourseTerm.setColumnName(selectCourseColumnName);
		selectCourseTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectCourseTerm.setValue(selectCourseId);
		selectCourseQueryTermList.add(selectCourseTerm);
		
		String courseDeletedColumnName = Course.getColumnName(Course.Columns.DELETED);
		selectCourseQueryTermList.add(notDeleted(courseDeletedColumnName));
	
		List<String> selectCourseColumnNameList = Course.getColumnNameList();
		
		String courseSortColumnName = Course.getColumnName(Course.Columns.COURSE_NUMBER);
		List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<>();
		Pair<String, ColumnOrder> courseOrderPair = new Pair<String, ColumnOrder>(courseSortColumnName, ColumnOrder.ASC);
		courseOrderByList.add(courseOrderPair);
		
		List<Course> courseList = coursesDao.select(selectCourseColumnNameList, selectCourseQueryTermList, courseOrderByList);
		
		// select instructorID from usersInstructors
		String selectLinkedColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID);
		Integer selectLinkedUserId = userId;
		
		List<QueryTerm> selectLinkedQueryTermList = new ArrayList<>();
		
		QueryTerm selectLinkedTerm = new QueryTerm();
		selectLinkedTerm.setColumnName(selectLinkedColumnName);
		selectLinkedTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectLinkedTerm.setValue(selectLinkedUserId);
		selectLinkedQueryTermList.add(selectLinkedTerm);
		
		String linkNotDeletedColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.DELETED);
		selectLinkedQueryTermList.add(notDeleted(linkNotDeletedColumnName));
		
		List<String> selectLinkedColumnNameList = InstructorUserLink.getColumnNameList();
		
		List<InstructorUserLink> linkedInstructorList = instructorUserLinksDao.select(selectLinkedColumnNameList, selectLinkedQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		
		SchedulerTabReturnObject stro = new SchedulerTabReturnObject(null, instructorList, courseList);
		
		if (!linkedInstructorList.isEmpty()) {
			stro.setLinkedInstructorId(linkedInstructorList.get(0).getInstructorId());
		}
		
		return stro;
	}
	
	
	public ValidateReturnObject validate(Integer year) throws SQLException
	{
		
		Boolean success = false;
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get all instructors
		
		String selectInstructorColumnName = Instructor.getColumnName(Instructor.Columns.ID);
		Integer selectInstructorId = 0;
	
		List<QueryTerm> selectInstructorQueryTermList = new ArrayList<>();
	
		QueryTerm selectInstructorTerm = new QueryTerm();
		selectInstructorTerm.setColumnName(selectInstructorColumnName);
		selectInstructorTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectInstructorTerm.setValue(selectInstructorId);
		selectInstructorQueryTermList.add(selectInstructorTerm);
		
		String instructorDeletedColumnName = Instructor.getColumnName(Instructor.Columns.DELETED);
		selectInstructorQueryTermList.add(notDeleted(instructorDeletedColumnName));
	
		List<String> selectInstructorColumnNameList = User.getColumnNameList();
		
		String instructorSortColumnName = Instructor.getColumnName(Instructor.Columns.FIRST_NAME);
		List<Pair<String, ColumnOrder>> instructorOrderByList = new ArrayList<>();
		Pair<String, ColumnOrder> instructorOrderPair = new Pair<String, ColumnOrder>(instructorSortColumnName, ColumnOrder.ASC);
		instructorOrderByList.add(instructorOrderPair);

		List<Instructor> instructorList = instructorsDao.select(selectInstructorColumnNameList, selectInstructorQueryTermList, instructorOrderByList);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		String selectCourseLoadColumnName = CourseLoad.getColumnName(CourseLoad.Columns.ID);
		Integer selectCourseLoadId = 0;
	
		List<QueryTerm> selectCourseLoadQueryTermList = new ArrayList<>();
	
		QueryTerm selectCourseLoadTerm = new QueryTerm();
		selectCourseLoadTerm.setColumnName(selectCourseLoadColumnName);
		selectCourseLoadTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectCourseLoadTerm.setValue(selectCourseLoadId);
		selectCourseLoadQueryTermList.add(selectCourseLoadTerm);
		
		String courseLoadDeletedColumnName = CourseLoad.getColumnName(CourseLoad.Columns.DELETED);
		selectInstructorQueryTermList.add(notDeleted(courseLoadDeletedColumnName));
	
		List<String> selectCourseLoadColumnNameList = CourseLoad.getColumnNameList();
		
		List<CourseLoad> courseLoadList = courseLoadsDao.select(selectCourseLoadColumnNameList, selectCourseLoadQueryTermList, new ArrayList<Pair<String, ColumnOrder>>());
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// iterate through each instructor to see if they meet minimum courseload requirements
		
		List<Instructor> retInstructorList = new ArrayList<Instructor>();
		
		List<Pair<Integer, Integer>> instructorListCoursesA = customDao.getAllInstructorsWithNumSections(year, "A");
		List<Pair<Integer, Integer>> instructorListCoursesB = customDao.getAllInstructorsWithNumSections(year, "B");
		List<Pair<Integer, Integer>> instructorListCoursesC = customDao.getAllInstructorsWithNumSections(year, "C");
		List<Pair<Integer, Integer>> instructorListCoursesD = customDao.getAllInstructorsWithNumSections(year, "D");
		
		Instructor instructor = null;
		CourseLoad courseLoad = null;
		
		for (int i = 0; i < instructorList.size(); i++) {
			instructor = instructorList.get(i);
			
			// get instructor's course load
			for (int j = 0; j < courseLoadList.size(); j++) {
				if (courseLoadList.get(j).getInstructorId() == instructor.getId()) {
					courseLoad = courseLoadList.get(j);
				}
			}
			if (courseLoad == null) {
				return new ValidateReturnObject(false, new ArrayList<Instructor>(), new ArrayList<Course>());
			}
			String cld = courseLoad.getLoadDescription();
			int frequency = Integer.parseInt(cld.substring(0, cld.indexOf(" ")));
			
			
			// get instructors taught courses for each term
			int icA = 0, icB = 0, icC = 0, icD = 0;
			for (int j = 0; j < instructorListCoursesA.size(); i++) {
				if (instructorListCoursesA.get(j).getValue1() == instructor.getId()) {
					icA = instructorListCoursesA.get(j).getValue2();
				}
			}
			for (int j = 0; j < instructorListCoursesB.size(); i++) {
				if (instructorListCoursesB.get(j).getValue1() == instructor.getId()) {
					icB = instructorListCoursesB.get(j).getValue2();
				}
			}
			for (int j = 0; j < instructorListCoursesC.size(); i++) {
				if (instructorListCoursesC.get(j).getValue1() == instructor.getId()) {
					icC = instructorListCoursesC.get(j).getValue2();
				}
			}
			for (int j = 0; j < instructorListCoursesD.size(); i++) {
				if (instructorListCoursesD.get(j).getValue1() == instructor.getId()) {
					icD = instructorListCoursesD.get(j).getValue2();
				}
			}
			
			// check frequency depending on whether by term, semester, or year
			if (cld.contains("per term"))
			{
				if (icA < frequency || icB < frequency || icC < frequency || icD < frequency)
				{
					retInstructorList.add(instructor);
				}
			} 
			else if (cld.contains("per semester"))
			{
				if (icA + icB < frequency || icC + icD < frequency)
				{
					retInstructorList.add(instructor);
				}
			} 
			else if (cld.contains("per year")) 
			{
				if (icA + icB + icC + icD < frequency)
				{
					retInstructorList.add(instructor);
				}
			}
			
			instructor = null;
			courseLoad = null;
		}
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// get all courses
		
		String selectCourseColumnName = Course.getColumnName(Course.Columns.ID);
		Integer selectCourseId = 0;
	
		List<QueryTerm> selectCourseQueryTermList = new ArrayList<>();
	
		QueryTerm selectCourseTerm = new QueryTerm();
		selectCourseTerm.setColumnName(selectCourseColumnName);
		selectCourseTerm.setComparisonOperator(ComparisonOperator.NOT_EQUAL);
		selectCourseTerm.setValue(selectCourseId);
		selectCourseQueryTermList.add(selectCourseTerm);
		
		String courseDeletedColumnName = Course.getColumnName(Course.Columns.DELETED);
		selectInstructorQueryTermList.add(notDeleted(courseDeletedColumnName));
	
		List<String> selectCourseColumnNameList = User.getColumnNameList();
		
		String courseSortColumnName = Course.getColumnName(Course.Columns.COURSE_NUMBER);
		List<Pair<String, ColumnOrder>> courseOrderByList = new ArrayList<>();
		Pair<String, ColumnOrder> courseOrderPair = new Pair<String, ColumnOrder>(courseSortColumnName, ColumnOrder.ASC);
		courseOrderByList.add(courseOrderPair);
		
		List<Course> courseList = coursesDao.select(selectCourseColumnNameList, selectCourseQueryTermList, courseOrderByList);
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// iterate through each course to see if they meet minimum course load requirements
		
		List<Pair<Integer, Integer>> coursesListSectionsA = customDao.getAllCoursesWithNumSections(year, "A");
		List<Pair<Integer, Integer>> coursesListSectionsB = customDao.getAllCoursesWithNumSections(year, "B");
		List<Pair<Integer, Integer>> coursesListSectionsC = customDao.getAllCoursesWithNumSections(year, "C");
		List<Pair<Integer, Integer>> coursesListSectionsD = customDao.getAllCoursesWithNumSections(year, "D");
		
		
		List<Course> retCourseList = new ArrayList<Course>();
		
		Course course;
		for (int i = 0; i < courseList.size(); i++)
		{
			course = courseList.get(i);
			String fd = course.getFrequency();
			int frequency = Integer.parseInt(fd.substring(0, fd.indexOf(" ")));
			
			int csA = 0, csB = 0, csC = 0, csD = 0;
			for (int j = 0; j < coursesListSectionsA.size(); i++) {
				if (coursesListSectionsA.get(j).getValue1() == course.getId()) {
					csA = coursesListSectionsA.get(j).getValue2();
				}
			}
			for (int j = 0; j < coursesListSectionsB.size(); i++) {
				if (coursesListSectionsB.get(j).getValue1() == course.getId()) {
					csB = coursesListSectionsB.get(j).getValue2();
				}
			}
			for (int j = 0; j < coursesListSectionsC.size(); i++) {
				if (coursesListSectionsC.get(j).getValue1() == course.getId()) {
					csC = coursesListSectionsC.get(j).getValue2();
				}
			}
			for (int j = 0; j < coursesListSectionsD.size(); i++) {
				if (coursesListSectionsD.get(j).getValue1() == course.getId()) {
					csD = coursesListSectionsD.get(j).getValue2();
				}
			}
			
			// check frequency depending on whether by term, semester, or year
			if (fd.contains("even year"))
			{
				if (year % 2 == 0 || csA + csB + csC + csD < frequency) {
					retCourseList.add(course);
				}
			}
			else if (fd.contains("odd year"))
			{
				if (year % 2 == 1 || csA + csB + csC + csD < frequency) {
					retCourseList.add(course);
				}
			}
			else if (fd.contains("per term"))
			{
				if (csA < frequency || csB < frequency || csC < frequency || csD < frequency)
				{
					retCourseList.add(course);
				}
			} 
			else if (fd.contains("per semester"))
			{
				if (csA + csB < frequency || csC + csD < frequency)
				{
					retCourseList.add(course);
				}
			} 
			else if (fd.contains("per year")) 
			{
				if (csA + csB + csC + csD < frequency)
				{
					retCourseList.add(course);
				}
			}
			
		}
		
		/*---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----*/
		
		// if lists are empty, schedule is valid
		
		if (retInstructorList.isEmpty() && retCourseList.isEmpty()) {
			success = true;
		}
		
		return new ValidateReturnObject(success, retInstructorList, retCourseList);
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
