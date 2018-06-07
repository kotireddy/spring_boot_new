package com.spring.boot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration("logoutSuccessHandler")
public class CustomLogoutHandler implements LogoutSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.
                                        getLogger(CustomLogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, Authentication authentication)
                                                throws IOException, ServletException {
        LOGGER.info("<<<<<<< Logout Success handler executing >>>>>>>");
    }
}
