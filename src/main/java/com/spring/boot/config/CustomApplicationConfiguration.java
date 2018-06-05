package com.spring.boot.config;

import java.util.Arrays;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);
	
	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource dataSource() throws IllegalArgumentException, NamingException {
		LOGGER.info("<<<<<<< Jndi DataSource Fetching >>>>>>>");
		if(Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
			LOGGER.info("<<<<<<< Jndi Prod DataSource Fetching >>>>>>>");
			JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
			bean.setJndiName("java:comp/env/jdbc/oxygen");
			bean.setProxyInterface(DataSource.class);
			bean.setLookupOnStartup(false);
			bean.afterPropertiesSet();
			return (DataSource) bean.getObject();
		}else {
			LOGGER.info("<<<<<<< UAT (OR) DEV DataSource Fetching >>>>>>>");
			DataSourceProperties dataSourceProperties = dataSourceProperties();
			return primaryDataSource(dataSourceProperties);
		}
		
	}
	
	/*@Bean
	public LocalSessionFactoryBean sessionFactory() throws IllegalArgumentException, NamingException {
		LOGGER.info("<<<<<<< SessionFactory Configuration >>>>>>>");
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		if(Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
			LOGGER.info("<<<<<<< Loading Production SessionFactory >>>>>>>");
			sessionFactory.setDataSource(dataSource());
			sessionFactory.setPackagesToScan("com.spring.boot.entity");
		}else {
			LOGGER.info("<<<<<<< Loading Dev (OR) Uat SessionFactory >>>>>>>");
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setPackagesToScan("com.spring.boot.entity");
		}
		
		return sessionFactory;
	}*/
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@ConfigurationProperties("spring.datasource")
	private HikariDataSource primaryDataSource(DataSourceProperties properties) {
		return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
				.build();
	}

	public static Connector getTomcatConnector(){
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}
	
}
