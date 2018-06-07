package com.spring.boot.handler;

import com.spring.boot.config.CustomSecurityConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration("failureHandler")
public class CustomFailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.
                                getLogger(CustomFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, AuthenticationException e)
                                                       throws IOException, ServletException {
        LOGGER.info("<<<<<<< Authentication Failure handler executing >>>>>>>");
    }
}
