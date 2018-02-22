package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.sqlutils.QueryTerm;

public interface CoursesDao extends Dao<Course>
{

	Course insertReturnModel(Course model, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException;

}
