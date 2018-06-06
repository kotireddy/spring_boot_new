package com.spring.boot.config;

import com.spring.boot.constants.ApplicationContants;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

@Configuration
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);

	@Autowired
	private PropertyLoaderConfiguration propertyLoaderConfiguration;

	@Bean
	@ConfigurationProperties(prefix = ApplicationContants.DATABASE_PROPS_CONST)
	public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
		LOGGER.info("<<< --- Loading HikariDataSource --- >>>");
		return dataSourceProperties.initializeDataSourceBuilder()
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
