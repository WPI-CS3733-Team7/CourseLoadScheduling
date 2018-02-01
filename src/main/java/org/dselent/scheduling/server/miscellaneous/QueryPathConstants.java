package org.dselent.scheduling.server.miscellaneous;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Register all custom SQL query files here
 * 
 * @author dselent
 *
 */
public class QueryPathConstants
{
	private static String BASE_QUERY_PATH = "sql" + File.separator + "Active" + File.separator;
	private static String SQL_EXTENSION = ".sql";

	private static String USERS_WITH_ROLE_PATH = BASE_QUERY_PATH + "CustomUsersWithRole" + SQL_EXTENSION;
	private static String SECTIONS_BY_INSTRUCTOR_PATH = BASE_QUERY_PATH + "CustomSectionsByInstructor" + SQL_EXTENSION;
	private static String SECTIONS_BY_COURSE_PATH = BASE_QUERY_PATH + "CustomSectionsByCourse" + SQL_EXTENSION;
	private static String INSTRUCTORS_WITH_NUM_SECTIONS_PATH = BASE_QUERY_PATH + "CustomAllInstructorsWithNumSections" + SQL_EXTENSION;
	private static String COURSES_WITH_NUM_SECTIONS_PATH = BASE_QUERY_PATH + "CustomAllCoursesWithNumSections" + SQL_EXTENSION;

	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	

	public static String USERS_WITH_ROLE_QUERY = readFile(USERS_WITH_ROLE_PATH);
	public static String SECTIONS_BY_INSTRUCTOR_QUERY = readFile(SECTIONS_BY_INSTRUCTOR_PATH);
	public static String SECTIONS_BY_COURSE_QUERY = readFile(SECTIONS_BY_COURSE_PATH);
	public static String INSTRUCTORS_WITH_NUM_SECTIONS_QUERY = readFile(INSTRUCTORS_WITH_NUM_SECTIONS_PATH);
	public static String COURSES_WITH_NUM_SECTIONS_QUERY = readFile(COURSES_WITH_NUM_SECTIONS_PATH);

	
	private QueryPathConstants()
	{
		
	}
	
	public static String readFile(String path)
	{
		byte[] encodedbytes = null;
		
		try
		{
			Resource resource = new ClassPathResource(path);
			encodedbytes = Files.readAllBytes(Paths.get(resource.getURI()));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
		return new String(encodedbytes);
	}

}
