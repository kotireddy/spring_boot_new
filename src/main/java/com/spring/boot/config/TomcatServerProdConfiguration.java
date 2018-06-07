package com.spring.boot.config;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "prod")
public class TomcatServerProdConfiguration extends TomcatServletWebServerFactory{

	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServerProdConfiguration.class);
	
	@Override
	protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
		LOGGER.info("<<<<<<< Tomcat Naming Context Enabled >>>>>>>");
		tomcat.enableNaming();
		tomcat.setConnector(CustomApplicationConfiguration.getTomcatConnector());
		return super.getTomcatWebServer(tomcat);
	}
	
	@Override
	protected void postProcessContext(Context context) {
		LOGGER.info("<<<<<<< JNDI DataSource Configuration >>>>>>>");
		ContextResource resource = new ContextResource();
		resource.setName("jdbc/oxygen");
		resource.setAuth("Container");
		resource.setType("javax.sql.DataSource");
		resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
		resource.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		resource.setProperty("jdbcUrl", "jdbc:mysql://localhost:3306/oxygen?useSSL=false");
		resource.setProperty("username", "root");
		resource.setProperty("password", "password");
		resource.setProperty("poolName", "springHikariCP");
		resource.setProperty("connectionTestQuery", "SELECT 1");
		resource.setProperty("cachePrepStmts", "true");
		resource.setProperty("prepStmtCacheSize", "250");
		resource.setProperty("prepStmtCacheSqlLimit", "2048");
		resource.setProperty("maximumPoolSize", "50");
		resource.setProperty("minimumIdle", "1");
		resource.setProperty("idleTimeout", "40000");
		resource.setProperty("maxLifetime", "50000");
		resource.setProperty("connectionTimeout", "30000");
		
		context.getNamingResources().addResource(resource);
		
		LOGGER.info("<<<<<<< Tomcat HTTPS Enable Configuration >>>>>>>");
		SecurityConstraint securityConstraint = new SecurityConstraint();
		securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
	}
	

	
	
	
	
	
	
	
	
}
