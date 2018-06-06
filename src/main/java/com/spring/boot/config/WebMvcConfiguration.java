package com.spring.boot.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer{

    private static final Logger LOGGER = LoggerFactory.
                                    getLogger(WebMvcConfiguration.class);

    private static final String[] RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/", "classpath:/templates/" };

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        LOGGER.info("<<<<<< Loading ServletHandlerConfigurer >>>>>>");
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        LOGGER.info("<<<<<< Loading ViewResolver >>>>>>");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        LOGGER.info(">>>>>> Loading ResourceHandlers <<<<<<");
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    RESOURCE_LOCATIONS);
        }
    }

    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        LOGGER.info(">>>>>> Loading MappingJackson2HttpMessageConverter Configuration <<<<<<");
        MappingJackson2HttpMessageConverter httpMessageConverter = new
                MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        httpMessageConverter.setObjectMapper(objectMapper);
        return httpMessageConverter;
    }

    @Bean
    public HttpMessageConverter<Object> xmlHttpMessageConverter(){
        LOGGER.info(">>>>>> Loading MarshallingHttpMessageConverter Configuration <<<<<<");
        MarshallingHttpMessageConverter httpMessageConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        httpMessageConverter.setMarshaller(xStreamMarshaller);
        return httpMessageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        LOGGER.info(">>>>>> Loading Message Converters Configuration <<<<<<");
        converters.add(jackson2HttpMessageConverter());
        //converters.add(xmlHttpMessageConverter());
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        LOGGER.info(">>>>>> Loading cors Registry Configuration <<<<<<");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("X-Auth-Token", "Content-Type")
                .exposedHeaders("header-1", "header-2")
                .allowCredentials(false)
                .maxAge(6000);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }*/
}
