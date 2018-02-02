package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.dselent.scheduling.server.config.AppConfig;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.CourseSection;
import org.dselent.scheduling.server.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CustomDaoTest
{
	@Autowired
	private CustomDao customDao;
	
	/*
	 * Not really an using this as a JUnit test
	 * More of an example on how to use the classes
	 */
    @Test
    @SuppressWarnings("unused")
    public void testCustomDao() throws SQLException
    {
    	Scanner scan = new Scanner(System.in);
		
    	List<User> userList = customDao.getAllUsersWithRole(1);
		System.out.println(userList);
		System.out.println();
		scan.next();
    	
		List<CourseSection> sectionsList = customDao.getSectionsByInstructor(29, 2018, "A");
    	System.out.println(sectionsList);
		System.out.println();
		scan.next();
    	
		sectionsList = customDao.getSectionsByCourse(1, 2018, "A");
    	System.out.println(sectionsList);
		System.out.println();
		scan.next();
    	
		List<Pair<Integer, Integer>> sectionCountPairsList = customDao.getAllInstructorsWithNumSections(2018, "D");
    	System.out.println(sectionCountPairsList);
		System.out.println();
		scan.next();
    	
		sectionCountPairsList = customDao.getAllCoursesWithNumSections(2018, "D");
    	System.out.println(sectionCountPairsList);
		System.out.println();
		scan.next();
		scan.close();
    }
}
