package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.miscellaneous.Pair;

/**
 * A couple of our custom queries return a result set containing two columns of integers that do not fit a model.
 * The integers consist of an instructor or course id in the first column and a count of the associate course sections in the second column.
 * In order to extract the data from these result sets this specialized extractor is required.
 * 
 * @author Leif Sahyun
 *
 */
public class IntegerPairExtractor extends Extractor<List<Pair<Integer, Integer>>>
{

	@Override
	public List<Pair<Integer, Integer>> extractData(ResultSet rs) throws SQLException {
		List<Pair<Integer, Integer>> resultList = new ArrayList<>();
		
		while(rs.next())
		{
			Integer databaseId = new Integer(rs.getInt(1)); //The course or instructor id
			Integer count = new Integer(rs.getInt(2)); //The count of associate course sections
			Pair<Integer, Integer> result = new Pair<Integer, Integer>(databaseId, count);
		
			resultList.add(result);
		}
		
		return resultList;
	}

	

}
