package com.spring.boot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;


@SpringBootApplication   //(exclude = JpaRepositoriesAutoConfiguration.class)
public class DemoApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		LOGGER.info("<<<<<< Starting of application >>>>>>");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("<<<<<< Loading Bean Definitions Names>>>>>>");
		for (String beanName : applicationContext.getBeanDefinitionNames()){
			LOGGER.info("{ "+ beanName +" } |" + " { Is Singleton Bean : " +
					applicationContext.isSingleton(beanName) + " }");
		}
	}
}
