package com.spring.boot.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = { "dev", "uat"})
public class TomcatServerDevConfiguration extends TomcatServletWebServerFactory{

	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatServerDevConfiguration.class);
	
	@Override
	protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
		LOGGER.info("<<<<<<< Tomcat Naming Context Enabled >>>>>>>");
		tomcat.enableNaming();
		tomcat.setConnector(CustomApplicationConfiguration.getTomcatConnector());
		return super.getTomcatWebServer(tomcat);
	}
	
	@Override
	protected void postProcessContext(Context context) {
		LOGGER.info("<<<<<<< Tomcat HTTPS Enable Configuration >>>>>>>");
		SecurityConstraint securityConstraint = new SecurityConstraint();
		securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
	}
	

	
	
	
	
	
	
	
	
}
