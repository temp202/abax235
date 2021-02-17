package com.tgl.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Query Manager Class used to build and execute queries using different parameters
 * @author Manisha Goyal
 */
public class QueryManager {
	private final static String className = "QueryManager";

	/**
	 * Static method to build select query
	 * @param cols
	 * @param conditions
	 * @param tblName
	 * @param groupByColName
	 * @param orderByColName
	 * @return String
	 */
	public static String buildSelectQuery(ArrayList<String> cols, ArrayList<String> conditions, 
				String tblName, String groupByColName, String orderByColName){
		StringBuilder query = new StringBuilder("select ");
		
		try{
			if(cols != null && cols.size() > 0){
				for(int i = 0; i < cols.size(); i++){
					if(i > 0){
						query.append(", ");
					}
					query.append((String)cols.get(i));
				}			
			}else{
				query.append("*");
			}
			
			if(tblName != null && !tblName.equals("")){
				if(!tblName.contains(" ")){
					query.append(" from `" + tblName + "`");
				}else{
					query.append(" from " + tblName);
				}	
			}
			
			if(conditions != null && conditions.size() > 0){
				query.append(" where ");
				for(int i = 0; i < conditions.size(); i++){
					if(i > 0){
						query.append(" and ");
					}
					query.append("(" + (String)conditions.get(i) + ")");
				}			
			}
			
			if(groupByColName != null && !groupByColName.trim().equals("")){
				query.append(" group by " + groupByColName);
			}
			
			if(orderByColName != null && !orderByColName.trim().equals("")){
				query.append(" order by " + orderByColName);
			}
			//System.out.println("select query: " + query.toString());
			//Logs.printInfoLog(groupByColName, query.toString());
			return query.toString();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			Logs.printErrorLog(className, e.getMessage());
		}
		return null;
	}
	
	/**
	 * Static method to build insert query
	 * @param colValues
	 * @param colValuesExQuotes
	 * @param tblName
	 * @return String
	 */
	public static String buildInsertQuery(Map<String, String> colValues, 
				Map<String, Object> colValuesExQuotes, String tblName){
		if((colValues != null && colValues.size() > 0) || (colValuesExQuotes != null && colValuesExQuotes.size() > 0)){
			StringBuilder query = new StringBuilder("insert into `" + tblName + "`");
			try{
				StringBuilder cols = new StringBuilder("");
				StringBuilder values = new StringBuilder("");
				
				if(colValues != null && colValues.size() > 0){
					Iterator<Entry<String, String>> entrySetIterator = colValues.entrySet().iterator();
					while (entrySetIterator.hasNext()) {
					    Entry<String, String> entry = (Entry<String, String>)entrySetIterator.next();
					    String val = "";
					    if(entry.getValue() != null) {
					    	val = (String)entry.getValue();
					    	val = val.replace("'", "''");
					    }
					    if(cols.toString().equals("")){
							cols.append((String)entry.getKey());
							values.append("'" + val + "'");
							//values.append("'" + StringEscapeUtils.escapeEcmaScript((String)entry.getValue()) + "'");
						}else{
							cols.append(", " + (String)entry.getKey());
							values.append(", '" + val + "'");
							//values.append(", '" + StringEscapeUtils.escapeEcmaScript((String)entry.getValue()) + "'");
						}
					}
				}
				if(colValuesExQuotes != null && colValuesExQuotes.size() > 0){
					Iterator<Entry<String, Object>> entrySetIterator = colValuesExQuotes.entrySet().iterator();
					while (entrySetIterator.hasNext()) {
					    Entry<String, Object> entry = (Entry<String, Object>)entrySetIterator.next();
					    if(cols.toString().equals("")){
							cols.append((String)entry.getKey());
							values.append(entry.getValue());
						}else{
							cols.append(", " + (String)entry.getKey());
							values.append(", " + entry.getValue());
						}
					}
				}

				query.append(" (" + cols.toString() + ") values (" + values.toString()  + ")");
				//System.out.println("insert query: " + query.toString());
				return query.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Logs.printErrorLog(className, e.getMessage());
			}
		}		
		return null;
	}
	
	/**
	 * Static method to build update query
	 * @param colValues
	 * @param colValuesExQuotes
	 * @param conditions
	 * @param tblName
	 * @return String
	 */
	public static String buildUpdateQuery(Map<String, String> colValues, 
			Map<String, Object> colValuesExQuotes, ArrayList<String> conditions, 
			String tblName, String orderByColName){
		StringBuilder query = new StringBuilder("update ");
		
		try{
			query.append("`" + tblName + "`");
			query.append(" set ");
			StringBuilder cols = new StringBuilder("");
			
			if(colValues != null && colValues.size() > 0){
				Iterator<Entry<String, String>> entrySetIterator = colValues.entrySet().iterator();
				while (entrySetIterator.hasNext()) {
				    Entry<String, String> entry = (Entry<String, String>)entrySetIterator.next();
				    String val = "";
				    if(entry.getValue() != null) {
				    	val = (String)entry.getValue();
				    	val = val.replace("'", "''");
				    }
				    if(cols.toString().equals("")){
				    	cols.append((String)entry.getKey() + "='" + val + "'");
						//cols.append((String)entry.getKey() + "='" + StringEscapeUtils.escapeEcmaScript((String)entry.getValue()) + "'");
					}else{
						cols.append(", " + (String)entry.getKey() + "='" + val + "'");
						//cols.append(", " + (String)entry.getKey() + "='" + StringEscapeUtils.escapeEcmaScript((String)entry.getValue()) + "'");
					}
				}
			}
			if(colValuesExQuotes != null && colValuesExQuotes.size() > 0){
				Iterator<Entry<String, Object>> entrySetIterator = colValuesExQuotes.entrySet().iterator();
				while (entrySetIterator.hasNext()) {
				    Entry<String, Object> entry = (Entry<String, Object>)entrySetIterator.next();
				    if(cols.toString().equals("")){
						cols.append((String)entry.getKey() + "=" + entry.getValue());
					}else{
						cols.append(", " + (String)entry.getKey() + "=" + entry.getValue());
					}
				}
			}
			
			query.append(cols.toString());
			
			if(conditions != null && conditions.size() > 0){
				query.append(" where ");
				for(int i = 0; i < conditions.size(); i++){
					if(i > 0){
						query.append(" and ");
					}
					query.append("(" + (String)conditions.get(i) + ")");
				}			
			}
			if(orderByColName != null && !orderByColName.trim().equals("")){
				query.append(" order by " + orderByColName);
			}
			//System.out.println("update query: " + query.toString());
			return query.toString();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			Logs.printErrorLog(className, e.getMessage());
		}
		return null;
	}
	
	/**
	 * Static method to build delete query
	 * @param conditions
	 * @param tblName
	 * @return String
	 */
	public static String buildDeleteQuery(ArrayList<String> conditions, String tblName){
		StringBuilder query = new StringBuilder("delete from ");
		
		try{
			query.append(tblName);
			
			if(conditions != null && conditions.size() > 0){
				query.append(" where ");
				for(int i = 0; i < conditions.size(); i++){
					if(i > 0){
						query.append(" and ");
					}
					query.append("(" + (String)conditions.get(i) + ")");
				}			
			}
			//System.out.println("query: " + query.toString());
			return query.toString();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
	    	Logs.printErrorLog(className, e.getMessage());
		}
		return null;
	}
}
