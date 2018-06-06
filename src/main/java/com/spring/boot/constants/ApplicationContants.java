package com.spring.boot.constants;

public class ApplicationContants {

	/** VIEWRESOLVER CONSTANTS **/
	public static final String VIEW_PREFIX="/WEB-INF/jsp/";
	public static final String VIEW_SUFFIX=".jsp";
	
	/** DATASOURCE PROPERTIES LOADING CONSTANTS **/
	public static final String DATABASE_PROPS_CONST = "spring.datasource";
	public static final String HIBERNATE_PROPS_CONST = "hibernate";
	public static final String HIBER_ENTITY_SCAN_PACKZAGE = "com.spring.boot.entity";
	
	
	
	/** HIBERNATE PROPERTIES CONFIGURATION **/
	public static final String HIBER_DIALECT_KEY = "hibernate.dialect";
	public static final String HIBER_SHOW_SQL_KEY = "hibernate.show_sql";
	public static final String HIBER_FORMAT_SQL_KEY= "hibernate.format_sql";
	public static final String HIBER_CURRE_SESS_CONTEXT_CLASS_KEY = "hibernate.current_session_context_class";
	public static final String HIBER_HBN2DDL_KEY = "hibernate.hbm2ddl.auto";
	
}
