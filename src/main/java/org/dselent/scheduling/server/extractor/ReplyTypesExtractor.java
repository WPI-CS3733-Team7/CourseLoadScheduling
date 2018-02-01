package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.model.ReplyType;

public class ReplyTypesExtractor extends Extractor<List<ReplyType>>{

	@Override
	public List<ReplyType> extractData(ResultSet rs) throws SQLException {
		
		List<ReplyType> resultList = new ArrayList<>();

		while(rs.next())
		{
			ReplyType result = new ReplyType();
				
			result.setId(rs.getInt(ReplyType.getColumnName(ReplyType.Columns.ID)));
			if(rs.wasNull())
			{
				result.setId(null);
			}
			
			result.setReplyType(rs.getString(ReplyType.getColumnName(ReplyType.Columns.REPLY_TYPE)));
			
			result.setDeleted(rs.getBoolean(ReplyType.getColumnName(ReplyType.Columns.DELETED)));
			if(rs.wasNull())
			{
				result.setDeleted(null);
			}
		
			resultList.add(result);
		}
			
		return resultList;	
		
	}

}
