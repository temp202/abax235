package com.tgl.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

/**
 * Database connection class
 * @author Manisha
 */
public class DbConnection {
	private final static String className = "DbConnection";
	private static String connString = "jdbc:mysql://localhost:3306/auto_parts";
	private static String username = "root";
	private static String password = "nitt";
	static Connection conn = null;
	static int connFailCount = 0;
	private final static boolean liveDbRequired = false;
	public final static String base_url = "http://localhost:8080/StripeMs/";
	
	/**
	 * Static method to get database connection
	 * @return Connection
	 */
	private static Connection getDatabaseConnection(){
		try{
			if (conn == null ||  conn.isClosed() || !conn.isValid(0)){
        		Class.forName("com.mysql.jdbc.Driver");

        		if(liveDbRequired){
	        		connString = "jdbc:mysql://166.62.118.66:3306/demotogg_autop3764";
	        		username = "demotogg_autop37";
	        		password = "shnk&&F=ZU2$BN@Y&B";
        		}
        		
        		Properties props = new Properties();
        	    props.put("user", username);
        	    props.put("password", password);
        	    props.put("autoReconnect", "true");
        		conn = DriverManager.getConnection(connString, props);
			}
        }catch (Exception e) {
        	e.printStackTrace();
        	conn = null;
		}
		return conn;
	}
	
	/**
	 * Method to execute stored procedure
	 * @param spStmt
	 * @return boolean
	 */
	public static boolean executeStoredProcedure(String spStmt){
		Connection con = null;
		boolean result = false;
		try{
			con = getDatabaseConnection();
			if(con != null){
				if(!spStmt.startsWith("call ")){
					spStmt = "call " + spStmt;
				}
				CallableStatement cs = con.prepareCall("{" + spStmt + "}");
				cs.execute();
				result = true;
				connFailCount = 0;
				cs.close();
			}
	    } catch(CommunicationsException ce){
	    	ce.printStackTrace();
	    	connFailCount++;
	    	if(connFailCount == 1){
	    		reCheckConnection();
	    		result = executeStoredProcedure(spStmt);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	Logs.printErrorLog(className, e.getMessage());
		}
		return result;
	}
	
	/**
	 * Method to execute update/delete query
	 * @param query
	 * @return boolean
	 */
	public static boolean executeUpdateQuery(String query){
		Connection con = null;
		boolean result = false;
		try{
			con = getDatabaseConnection();
			if(con != null){
				Statement stmt = con.createStatement();
				if(stmt != null){
					stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		    		result = true;
		    		connFailCount = 0;
					stmt.close();
				}
			}
	    } catch(CommunicationsException ce){
	    	ce.printStackTrace();
	    	connFailCount++;
	    	if(connFailCount == 1){
	    		reCheckConnection();
	    		result = executeUpdateQuery(query);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
			Logs.printErrorLog(className, e.getMessage());
		}
		return result;
	}
	
	/**
	 * Method to execute insert query
	 * @param query
	 * @return int
	 */
	public static int executeInsertQuery(String query){
		Connection con = null;
		int pk = 0;
		try{
			con = getDatabaseConnection();
			if(con != null){
				Statement stmt = con.createStatement();
				if(stmt != null){
					stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs != null){
    					if(rs.next())
    						pk = rs.getInt(1);
    					rs.close();
    				}else{
    					pk = -1;
    					System.out.println("GeneratedKey ResultSet is null");
    				}
		    		connFailCount = 0;
					stmt.close();
				}
			}
	    } catch(CommunicationsException ce){
	    	ce.printStackTrace();
	    	connFailCount++;
	    	if(connFailCount == 1){
	    		reCheckConnection();
	    		pk = executeInsertQuery(query);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
			Logs.printErrorLog(className, e.getMessage());
		}
		return pk;
	}
	
	/**
	 * Method to re-check database connection while getting CommunicationsException
	 */
	private static void reCheckConnection(){
		if(connFailCount == 1){
			getDatabaseConnection();
		}
	}
	
	/**
	 * Method to execute select query
	 * @param query
	 * @return ResultSet
	 */
	public static ResultSet executeQuery(String query){
		Connection con = null;
		try{
			con = getDatabaseConnection();
			if(con != null){
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				if(stmt != null){
					ResultSet rs = stmt.executeQuery(query);
		    		connFailCount = 0;
		    		if(rs != null)
		    			return rs;
		    		else
		    			stmt.close();
				}	
			}
	    } catch(CommunicationsException ce){
	    	ce.printStackTrace();
	    	connFailCount++;
	    	if(connFailCount == 1){
	    		reCheckConnection();
	    		return executeQuery(query);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
			Logs.printErrorLog(className, e.getMessage());
		}
		return null;
	}
}
