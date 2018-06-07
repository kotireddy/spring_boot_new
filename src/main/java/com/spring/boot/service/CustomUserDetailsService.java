package com.spring.boot.service;

import com.spring.boot.entity.CustomUserDetails;
import com.spring.boot.entity.Users;
import com.spring.boot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private static final Logger LOGGER = LoggerFactory.
                                            getLogger(CustomUserDetailsService.class);
    @Autowired
	private UserRepository userRepository;

	@Override
    @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("<<<<<<< LoadUserByUsername Configuration >>>>>>>");
        Optional<Users> optionalUsers = userRepository.findByName(username);
        optionalUsers.orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        return optionalUsers.map(CustomUserDetails::new).get();
	}
}
