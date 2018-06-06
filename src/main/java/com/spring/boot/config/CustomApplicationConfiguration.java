package com.spring.boot.config;

import com.spring.boot.constants.ApplicationContants;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.connector.Connector;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class CustomApplicationConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomApplicationConfiguration.class);

	@Autowired
	private PropertyLoaderConfiguration propertyLoaderConfiguration;

	@Bean
	@ConfigurationProperties(prefix = ApplicationContants.DATABASE_PROPS_CONST)
	public HikariDataSource dataSource() {
		LOGGER.info("<<< --- Loading HikariDataSource --- >>>");
		return propertyLoaderConfiguration.dataSourceProperties().initializeDataSourceBuilder()
									.type(HikariDataSource.class).build();
	}

	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactoryBean(){
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new
										LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan("com.spring.boot.entity");
		entityManagerFactory.setPersistenceUnitName("default");
		entityManagerFactory.afterPropertiesSet();
		return entityManagerFactory.getObject();
	}

    @Bean
	public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
		if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactoryBean());
		return txManager;
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
