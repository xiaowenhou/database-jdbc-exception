package com.putaoteng.jdbc.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static Logger logger = Logger.getLogger(Log.class);
	
	public static void loggerCreate(LogLevel logLevel, String message){
		PropertyConfigurator.configure("D:\\test\\jdbc-template\\src\\main\\resources\\log\\log4j.properties");
		
		switch (logLevel) {
		case DEBUG:
			logger.debug(message);
			break;
		case INFO:
			logger.info(message);
			break;
		case WARN:
			logger.warn(message);
			break;
		case ERROR:
			logger.error(message);
			break;
		default:
			break;
		}
	}
}
