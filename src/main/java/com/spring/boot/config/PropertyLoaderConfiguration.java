package com.spring.boot.config;

import com.spring.boot.constants.ApplicationContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import java.util.Properties;



@Configuration
@ConfigurationProperties(prefix = ApplicationContants.HIBERNATE_PROPS_CONST)
public class PropertyLoaderConfiguration {

	private static Logger logger = LoggerFactory.getLogger(PropertyLoaderConfiguration.class);
	
	@Autowired
	private Environment environment;
	
	/**
	 * Method to load Hikari Database Properties 
	 * @return DataSourceProperties
	 */
	@Bean
	@Primary
	@ConfigurationProperties(prefix = ApplicationContants.DATABASE_PROPS_CONST)
	public DataSourceProperties dataSourceProperties() {
		logger.info("<<< --- Loading DataSourceProperties --- >>>");
		return new DataSourceProperties();
	}
	
	/**
	 * Method to load Hibernate Properties
	 * @return
	 */
	@Bean
	public Properties hibernateProperties() {
		logger.info("<<< --- Loading Properties for Hibernate --- >>>");
		Properties properties = new Properties();
		properties.put(ApplicationContants.HIBER_DIALECT_KEY, 
				environment.getRequiredProperty(ApplicationContants.HIBER_DIALECT_KEY));
		properties.put(ApplicationContants.HIBER_SHOW_SQL_KEY, 
				environment.getRequiredProperty(ApplicationContants.HIBER_SHOW_SQL_KEY));
		properties.put(ApplicationContants.HIBER_CURRE_SESS_CONTEXT_CLASS_KEY, 
				environment.getRequiredProperty(ApplicationContants.HIBER_CURRE_SESS_CONTEXT_CLASS_KEY));
		properties.put(ApplicationContants.HIBER_HBN2DDL_KEY, 
				environment.getRequiredProperty(ApplicationContants.HIBER_HBN2DDL_KEY));
		return properties;
	}
	
}
