package org.dselent.scheduling.server.service;

import java.sql.SQLException;

import org.dselent.scheduling.server.dto.CourseSectionDto;
import org.dselent.scheduling.server.model.CourseSection;
import org.springframework.stereotype.Service;

@Service
public interface SectionsService
{
	/**
	 * Handles selecting, editing, and creating instructors in the system
	 * Inserts instructors into the database and edits existing instructors
	 */
    public CourseSection editSection(CourseSectionDto c) throws SQLException;
}
