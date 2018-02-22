package org.dselent.scheduling.server.dao;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.sqlutils.QueryTerm;

public interface InstructorsDao extends Dao<Instructor>
{
	Instructor insertReturnModel(Instructor model, List<String> insertColumnNameList, List<String> keyHolderColumnNameList) throws SQLException;
}
