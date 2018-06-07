package com.spring.boot.config;

import com.spring.boot.constants.ApplicationContants;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableJpaRepositories(basePackages = {"com.spring.boot.repository"})
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);

	@Autowired
	private Environment environment;

	@Autowired
	private PropertyLoaderConfiguration propertyLoaderConfiguration;

	@Bean
	@ConfigurationProperties(prefix = ApplicationContants.DATABASE_PROPS_CONST)
	public DataSource dataSource() {
		LOGGER.info("<<< --- Loading HikariDataSource --- >>>");
		try{
			if(Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
				LOGGER.info("<<<<<<< Jndi Prod DataSource Fetching >>>>>>>");
				JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
				bean.setJndiName("java:comp/env/jdbc/oxygen");
				bean.setProxyInterface(DataSource.class);
				bean.setLookupOnStartup(false);
				bean.afterPropertiesSet();
				return (DataSource)bean.getObject();
			}else{
				LOGGER.info("<<<<<<< UAT (OR) DEV DataSource Fetching >>>>>>>");
				return propertyLoaderConfiguration.dataSourceProperties()
						.initializeDataSourceBuilder().type(HikariDataSource.class).build();
			}
		}catch(Exception e){
			return null;
		}

	}

	/*@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.spring.boot.entity");
		sessionFactory.setHibernateProperties(propertyLoaderConfiguration.hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager(){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}*/


	/*@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new
										LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setJpaVendorAdapter(getHibernateJpaVendorAdapter());
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("com.spring.boot.entity");
		entityManagerFactory.setPersistenceUnitName("default");
		entityManagerFactory.setJpaProperties(propertyLoaderConfiguration.hibernateProperties());
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(true);
		return vendorAdapter;
	}
*/
	public static Connector getTomcatConnector(){
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}


}
