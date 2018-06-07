package com.spring.boot.service;

import com.spring.boot.constants.QueryConstants;
import com.spring.boot.entity.CustomUserDetails;
import com.spring.boot.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.
                                            getLogger(CustomUserDetailsService.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("<<<<<<< LoadUserByUsername Configuration >>>>>>>");
        Optional<Users> optionalUsers = Optional.of(entityManager.createQuery(QueryConstants.USER_LOGIN_QUERY, Users.class)
                .setParameter("username", username).getSingleResult());
        optionalUsers.
                orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return optionalUsers.map(CustomUserDetails::new).get();
	}
}
