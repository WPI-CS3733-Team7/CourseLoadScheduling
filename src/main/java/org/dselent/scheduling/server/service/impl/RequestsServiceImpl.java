package org.dselent.scheduling.server.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.dao.InstructorUserLinksDao;
import org.dselent.scheduling.server.dao.RequestsDao;
import org.dselent.scheduling.server.dao.UserRolesDao;
import org.dselent.scheduling.server.dao.UsersRolesLinksDao;
import org.dselent.scheduling.server.miscellaneous.Pair;
import org.dselent.scheduling.server.model.InstructorUserLink;
import org.dselent.scheduling.server.model.Request;
import org.dselent.scheduling.server.model.UserRole;
import org.dselent.scheduling.server.model.UsersRolesLink;
import org.dselent.scheduling.server.service.RequestsService;
import org.dselent.scheduling.server.sqlutils.ColumnOrder;
import org.dselent.scheduling.server.sqlutils.ComparisonOperator;
import org.dselent.scheduling.server.sqlutils.QueryTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class RequestsServiceImpl implements RequestsService{

	@Autowired
	InstructorUserLinksDao instructorUserLinksDao;
	
	@Autowired
	RequestsDao requestsDao;
	
	@Autowired
	UsersRolesLinksDao usersRolesLinksDao;
	
	@Autowired
	UserRolesDao userRolesDao;
	
	@Override
	public List<Request> page(@PathVariable("user_id") Integer userId) throws SQLException {
		
		//determine user role
		String selectUserRoleLinkColumn = UsersRolesLink.getColumnName(UsersRolesLink.Columns.USER_ID);
		Integer selectUserId = userId;
		
		List<QueryTerm> selectUserRoleLinkList = new ArrayList<>();
		
		QueryTerm selectUserRoleLinkTerm = new QueryTerm();
		selectUserRoleLinkTerm.setColumnName(selectUserRoleLinkColumn);
		selectUserRoleLinkTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectUserRoleLinkTerm.setValue(selectUserId);
		selectUserRoleLinkList.add(selectUserRoleLinkTerm);
		
		List<String> selectUserRoleLinkColumnList = UsersRolesLink.getColumnNameList();
		
		List<UsersRolesLink> userRoleLinkList = usersRolesLinksDao.select(selectUserRoleLinkColumnList, selectUserRoleLinkList, null);
		
		String selectUserRoleColumn = UserRole.getColumnName(UserRole.Columns.ROLE_NAME);
		
		List<QueryTerm> selectUserRoleList = new ArrayList<>();
		
		QueryTerm selectUserRoleTerm = new QueryTerm();
		selectUserRoleTerm.setColumnName(selectUserRoleColumn);
		selectUserRoleTerm.setComparisonOperator(ComparisonOperator.EQUAL);
		selectUserRoleTerm.setValue("ADMIN");
		selectUserRoleList.add(selectUserRoleTerm);
		
		List<String> selectUserRoleColumnList = UserRole.getColumnNameList();
		
		List<UserRole> userRoleList = userRolesDao.select(selectUserRoleColumnList, selectUserRoleList, null);
		
		boolean isAdmin = false;
		
		for(UserRole ur: userRoleList) {
			for(UsersRolesLink url: userRoleLinkList) {
				if(ur.getId() == url.getRoleId()) {
					isAdmin = true;
					break;
				}else {
					isAdmin = false;
				}
			}
		}
		
		if(isAdmin) {
		
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
		
			// i	f user is not linked to instructor, send empty list
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
		}else {
			String selectRequestColumnName = Request.getColumnName(Request.Columns.REQUESTER_ID);
			Integer selectRequester = userId;
			
			List<QueryTerm> selectRequestList = new ArrayList<>();
			
			QueryTerm selectRequestTerm = new QueryTerm();
			selectRequestTerm.setColumnName(selectRequestColumnName);
			selectRequestTerm.setComparisonOperator(ComparisonOperator.EQUAL);
			selectRequestTerm.setValue(selectRequester);
			selectRequestList.add(selectRequestTerm);
			
			List<String> selectRequestColumnList = Request.getColumnNameList();
			
			List<Request> selectedRequestList = requestsDao.select(selectRequestColumnList, selectRequestList, null);
			
			return selectedRequestList;
		} 
	}

	public List<Request> submitRequest(Integer userId, Request request) throws SQLException
	{
		// get instructor id that is linked to userId
		List<QueryTerm> selectQueryTermList = new ArrayList<>();
		
		QueryTerm selectInstructorId = new QueryTerm();
		selectInstructorId.setColumnName(InstructorUserLink.getColumnName(InstructorUserLink.Columns.LINKED_USER_ID));
		selectInstructorId.setComparisonOperator(ComparisonOperator.EQUAL);
		selectInstructorId.setValue(userId);
    		selectQueryTermList.add(selectInstructorId);
    	
    		List<String> columnNameList = new ArrayList<String>();
    		String columnName = InstructorUserLink.getColumnName(InstructorUserLink.Columns.INSTRUCTOR_ID);
    		columnNameList.add(columnName);
    	
    		List	<Pair<String, ColumnOrder>> orderByList = new ArrayList<>();
    		Pair<String, ColumnOrder> orderPair1 = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
    		orderByList.add(orderPair1);
    	
    		// if no corresponding id (ie list from select is empty), return an error
    		
    		List<InstructorUserLink> selectedInstructorList = instructorUserLinksDao.select(columnNameList, selectQueryTermList, orderByList);
    		
    		if (selectedInstructorList.isEmpty() == true) {
    			return null;
    		} else {
    			request.setRequesterId(userId);
    	
		// insert new entry into requests table with instructor's id, requesttype, description, replytype = "NO RESPONSE"
		List<String> requestInsertColumnNameList = new ArrayList<>();
    		List<String> requestKeyHolderColumnNameList = new ArrayList<>();
    	
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.REQUESTER_ID));
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.REQUEST_TYPE));
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.REQUEST_DETAILS));
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.REPLY));
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.REPLY_TYPE)); //ID 5 hard coded
    		requestInsertColumnNameList.add(Request.getColumnName(Request.Columns.DELETED));
    	
    		requestKeyHolderColumnNameList.add(Request.getColumnName(Request.Columns.ID));
    		requestKeyHolderColumnNameList.add(Request.getColumnName(Request.Columns.CREATED_AT));
    		requestKeyHolderColumnNameList.add(Request.getColumnName(Request.Columns.UPDATED_AT));
    	
    		//make new model and populate
    	
    		//Request newRequestModel = new Request();
    	
    	
    		requestsDao.insert(request, requestInsertColumnNameList, requestKeyHolderColumnNameList);
		
		// select all from requests table, where requester_id = instructor's id
		
    		List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
		
		QueryTerm selectRequest = new QueryTerm();
		selectRequest.setColumnName(Request.getColumnName(Request.Columns.REQUESTER_ID));
		selectRequest.setComparisonOperator(ComparisonOperator.EQUAL);
		selectRequest.setValue(selectInstructorId);
		selectQueryTermList2.add(selectRequest);
    	
		List<String> columnNameList2 = new ArrayList<String>();
    		String RequestColumnName1 = Request.getColumnName(Request.Columns.ID);
    		columnNameList2.add(RequestColumnName1);
  
    		String RequestColumnName2 = Request.getColumnName(Request.Columns.REQUESTER_ID);
    		columnNameList2.add(RequestColumnName2);
    	
    		String RequestColumnName3 = Request.getColumnName(Request.Columns.REQUEST_TYPE);
    		columnNameList2.add(RequestColumnName3);
    	
    		String RequestColumnName4 = Request.getColumnName(Request.Columns.REQUEST_DETAILS);
    		columnNameList2.add(RequestColumnName4);
    
    		String RequestColumnName5 = Request.getColumnName(Request.Columns.REPLY_TYPE);
    		columnNameList2.add(RequestColumnName5);
    	
    		String RequestColumnName6 = Request.getColumnName(Request.Columns.CREATED_AT);
    		columnNameList2.add(RequestColumnName6);
    	
    		String RequestColumnName7 = Request.getColumnName(Request.Columns.UPDATED_AT);
    		columnNameList2.add(RequestColumnName7);
    
    		String RequestColumnName8 = Request.getColumnName(Request.Columns.DELETED);
    		columnNameList2.add(RequestColumnName8);
    		
    		String RequestColumnName9 = Request.getColumnName(Request.Columns.REPLY);
    		columnNameList2.add(RequestColumnName9);
    	
    	
    		List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
    		Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(columnName, ColumnOrder.ASC);
    		orderByList2.add(orderPair2);
    	
    		List<Request> selectedRequests = requestsDao.select(columnNameList2, selectQueryTermList2, orderByList2);
    	
		// return the list from the select statement
		return selectedRequests;  	
    		}
		}
	
	public List<Request> submitResponse(Integer requestId, String replyType, String reply) throws SQLException {
		// TODO Auto-generated method stub		
		// set reply type id of request to id of inserted reply type
		
		String updateColumnName = Request.getColumnName(Request.Columns.REPLY_TYPE);
		String updateColumnName2 = Request.getColumnName(Request.Columns.REQUESTER_ID);
		String updateColumnName3 = Request.getColumnName(Request.Columns.REPLY);
		List<QueryTerm> updateQueryTermList = new ArrayList<>();
    	
    	QueryTerm updateUseNameTerm = new QueryTerm();
    	updateUseNameTerm.setColumnName(updateColumnName2);
    	updateUseNameTerm.setComparisonOperator(ComparisonOperator.EQUAL);
    	updateUseNameTerm.setValue(requestId);
    	updateQueryTermList.add(updateUseNameTerm);
    	
    	requestsDao.update(updateColumnName, replyType, updateQueryTermList);
    	requestsDao.update(updateColumnName3, reply, updateQueryTermList);    	
    	// RequestList after being updated
    	
    	List<QueryTerm> selectQueryTermList2 = new ArrayList<>();
		
		QueryTerm selectRequest = new QueryTerm();
		selectRequest.setColumnName(updateColumnName2);
		selectRequest.setComparisonOperator(ComparisonOperator.EQUAL);
		selectRequest.setValue(requestId);
    	selectQueryTermList2.add(selectRequest);
    	
    	List<String> columnNameList2 = new ArrayList<String>();
    	String RequestColumnName1 = Request.getColumnName(Request.Columns.ID);
    	columnNameList2.add(RequestColumnName1);
  
    	String RequestColumnName2 = Request.getColumnName(Request.Columns.REQUESTER_ID);
    	columnNameList2.add(RequestColumnName2);
    	
    	String RequestColumnName3 = Request.getColumnName(Request.Columns.REQUEST_TYPE);
    	columnNameList2.add(RequestColumnName3);
    	
    	String RequestColumnName4 = Request.getColumnName(Request.Columns.REQUEST_DETAILS);
    	columnNameList2.add(RequestColumnName4);
    
    	String RequestColumnName5 = Request.getColumnName(Request.Columns.REPLY_TYPE);
    	columnNameList2.add(RequestColumnName5);
    	
    	String RequestColumnName6 = Request.getColumnName(Request.Columns.CREATED_AT);
    	columnNameList2.add(RequestColumnName6);
    	
    	String RequestColumnName7 = Request.getColumnName(Request.Columns.UPDATED_AT);
    	columnNameList2.add(RequestColumnName7);
    
    	String RequestColumnName8 = Request.getColumnName(Request.Columns.DELETED);
    	columnNameList2.add(RequestColumnName8);
    	
    	String RequestColumnName9 = Request.getColumnName(Request.Columns.REPLY);
	columnNameList2.add(RequestColumnName9);
    	
 
    	
    	List<Pair<String, ColumnOrder>> orderByList2 = new ArrayList<>();
    	Pair<String, ColumnOrder> orderPair2 = new Pair<String, ColumnOrder>(updateColumnName2, ColumnOrder.ASC);
    	orderByList2.add(orderPair2);
    	
    	List<Request> selectedRequests = requestsDao.select(columnNameList2, selectQueryTermList2, orderByList2);
    	
	return selectedRequests;
	}
	
}
