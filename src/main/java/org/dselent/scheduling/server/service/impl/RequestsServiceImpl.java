package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.RequestsDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.Request;
import org.dselent.scheduling.server.service.RequestsService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestsServiceImpl implements RequestsService{

	@Autowired
	InstructorUserLinksDao instructorUserLinksDao;
	
	@Autowired
	RequestsDao requestsDao;
	
	@Override
	public List<Request> page(Integer userId) throws SQLException {

		// get instructor who is linked to userId
		String selectLinkedColumnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID);
		Integer selectLinkedUserId = userId;
		
		List<QueryTerm> selectLinkedQueryTermList = new ArrayList<>();
		
		QueryTerm selectLinkedTerm = new QueryTerm();
		selectLinkedTerm.setColumnName(selectLinkedColumnName);
		selectLinkedTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectLinkedTerm.setValue(selectLinkedUserId);
		
		List<String> selectLinkedColumnNameList = InstructorUserLink.getColumnNameList();
		
		List<InstructorUserLink> linkedInstructorList = instructorUserLinksDao.select(selectLinkedColumnNameList, selectLinkedQueryTermList, null);
		
		// if user is not linked to instructor, send empty list
		if (linkedInstructorList.isEmpty()) {
			return new ArrayList<Request>();
		}
		
		// get all requests that come from the userId's linked instructorId
		String selectRequestColumnName = Request.getColumnName(Request.Columns.REQUESTER_ID);
		Integer selectRequestInstructorId = linkedInstructorList.get(0).getInstructorId();
    	
		List<QueryTerm> selectRequestQueryTermList = new ArrayList<>();
    	
		QueryTerm selectRequestTerm = new QueryTerm();
		selectRequestTerm.setColumnName(selectRequestColumnName);
		selectRequestTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    		selectRequestTerm.setValue(selectRequestInstructorId);
    		selectRequestQueryTermList.add(selectRequestTerm);
    	
    		List<String> selectRequestColumnNameList = Request.getColumnNameList();
    	
    		String requestSortColumnName = Request.getColumnName(Request.Columns.UPDATED_AT);
    		List<Pair<String, ColumnOrder>> requestOrderByList = new ArrayList<>();
        	Pair<String, ColumnOrder> requestOrderPair = new Pair<String, ColumnOrder>(requestSortColumnName, ColumnOrder.DESC);
        	requestOrderByList.add(requestOrderPair);
    		
		List<Request> selectedRequestList = requestsDao.select(selectRequestColumnNameList, selectRequestQueryTermList, requestOrderByList);

		return selectedRequestList;
	}

}
