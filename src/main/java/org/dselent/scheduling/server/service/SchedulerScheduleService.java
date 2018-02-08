package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.model.User;
import org.dselent.scheduling.server.returnobject.SchedulerTabReturnObject;
import org.dselent.scheduling.server.returnobject.ValidateReturnObject;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public interface SchedulerScheduleService {

	public SchedulerTabReturnObject page(Integer userId) throws SQLException;
	public ValidateReturnObject validate(Integer year) throws SQLException;

}
