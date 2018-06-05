package com.spring.boot.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.hibernate.SessionFactory;
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
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);
	
	@Autowired
	private Environment environment;

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
				DataSourceProperties dataSourceProperties = dataSourceProperties();
				return primaryDataSource(dataSourceProperties);
			}
		}catch(Exception e){
			return null;
		}

	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.spring.boot.entity");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.current_session_context_class",
				"org.springframework.orm.hibernate5.SpringSessionContext");
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory.getObject();
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

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
