package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.Request;

public class RequestsExtractor extends Extractor<List<Request>>{

	@Override
	public List<Request> extractData(ResultSet rs) throws SQLException {
		
		List<Request> resultList = new ArrayList<>();

		while(rs.next())
		{
			Request result = new Request();
				
			result.setId(rs.getInt(Request.getColumnName(Request.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setRequesterId(rs.getInt(Request.getColumnName(Request.Columns.REQUESTER_ID)));
			if(rs.wasNull())
			{
				result.setRequesterId(null);
			}
			
			result.setRequestType(rs.getString(Request.getColumnName(Request.Columns.REQUEST_TYPE)));
			if(rs.wasNull())
			{
				result.setRequestType(null);
			}
			
			result.setRequestDetails(rs.getString(Request.getColumnName(Request.Columns.REQUEST_DETAILS)));
			result.setReply(rs.getString(Request.getColumnName(Request.Columns.REPLY)));
			
			result.setReplyType(rs.getString(Request.getColumnName(Request.Columns.REPLY_TYPE)));
			if(rs.wasNull())
			{
				result.setReplyType(null);
			}
			
			result.setCreatedAt(rs.getTimestamp(Request.getColumnName(Request.Columns.CREATED_AT)));
			result.setUpdatedAt(rs.getTimestamp(Request.getColumnName(Request.Columns.UPDATED_AT)));
		
			result.setDeleted(rs.getBoolean(Request.getColumnName(Request.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setRequestType(null);
			}
			
			resultList.add(result);
		}
			
		return resultList;	
	}

}
