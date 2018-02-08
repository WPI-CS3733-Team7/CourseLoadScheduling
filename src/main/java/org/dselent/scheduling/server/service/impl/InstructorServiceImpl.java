package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorsDao;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.service.InstructorService;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InstructorServiceImpl implements InstructorService
{
	
	@Autowired
	private InstructorsDao instructorsDao;
	
    public InstructorServiceImpl()
    {
    	//
    }

	@Override
	public void selectInstructor() {
		// TODO Auto-generated method stub
	}

	@Override
	public Instructor editInstructor(Instructor in) throws SQLException {
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
		return in;
	}
    
       

}
