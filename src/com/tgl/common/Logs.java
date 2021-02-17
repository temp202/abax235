package com.tgl.common;

import org.apache.log4j.Logger;

/**
 * This class contains methods to maintain logs 
 * @author Manisha
 */
public class Logs {
	static Logger logger = Logger.getLogger("");
	
	/**
	 * Default Constructor
	 */
	public Logs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method to print information logs to the log file
	 * @param className
	 * @param str
	 */
    public static void printInfoLog(String className, String str){
    	Logs log = new Logs();
    	String userDetail = log.userDetail();
        str = userDetail + "[" + className + "]" + str;
        logger.info(str);
    }

    /**
	 * Method to print debug logs to the log file
	 * @param className
	 * @param str
	 */
    public static void printDebugLog(String className, String str){
    	Logs log = new Logs();
    	String userDetail = log.userDetail();
        str = userDetail + "[" + className + "]" + str;
        logger.debug(str);
    }

    /**
	 * Method to print error logs to the log file
	 * @param className
	 * @param str
	 */
    public static void printErrorLog(String className, String str){
    	Logs log = new Logs();
    	String userDetail = log.userDetail();
        str = userDetail + "[" + className + "]" + str;
        logger.error(str);
    }
    
    /**
     * Method to get user details from session
     * @return String
     */
    private String userDetail(){
    	String detail = "";
    	detail = "[]\t[]\t";
    	
    	return detail;
    }   
}