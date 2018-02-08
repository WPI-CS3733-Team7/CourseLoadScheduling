package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.RegisterUserDto;
import org.dselent.scheduling.server.model.Instructor;
import org.dselent.scheduling.server.returnobject.SelectInstructorReturnObject;
import org.springframework.stereotype.Service;

/**
 * Service layer to specify all business logic. Calls the dao layer when data retrieval is needed.
 * Interfaces specify the behavior and the implementation provide the specific implementations.
 *
 */
@Service
public interface InstructorService
{
	/**
	 * Handles selecting, editing, and creating instructors in the system
	 * Inserts instructors into the database and edits existing instructors
	 */
	public SelectInstructorReturnObject selectInstructor(Instructor i);
    public Instructor editInstructor(Instructor i) throws SQLException;
}
