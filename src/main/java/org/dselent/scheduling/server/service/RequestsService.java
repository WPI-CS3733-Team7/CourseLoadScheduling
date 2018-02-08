package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.model.Request;

public interface RequestsService {

	public List<Request> page(Integer userId) throws SQLException;
	
}
