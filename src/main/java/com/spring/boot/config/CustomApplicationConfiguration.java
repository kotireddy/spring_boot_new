package com.spring.boot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAutoConfiguration@EnableTransactionManagement
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);
	
	@Autowired
	private Environment environment;

	@Autowired
	private PropertyLoaderConfiguration propertyLoaderConfiguration;

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.spring.boot.entity");
		sessionFactory.setHibernateProperties(propertyLoaderConfiguration.hibernateProperties());
		return sessionFactory.getObject();
	}

	@Bean
	public DataSource dataSource() {
		LOGGER.info("<<<<<<< Jndi DataSource Fetching >>>>>>>");
		try{
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
				return primaryDataSource();
			}
		}catch(Exception e){
			return null;
		}

	}

	@Bean
	public HibernateTransactionManager transactionManager() {
        LOGGER.info("<<< --- Loading HibernateTransactionManager --- >>>");
		HibernateTransactionManager transactionManager = new
				HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
        return transactionManager;
    }

	@Bean
    public HikariDataSource primaryDataSource() {
        LOGGER.info("<<< --- Loading HikariDataSource --- >>>");
        return propertyLoaderConfiguration.dataSourceProperties().initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
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
