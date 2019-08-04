package com.poc.loginapp.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.poc.loginapp.model.User;
import com.poc.loginapp.repository.UserRepository;

@Service
public class UserDetailsServiceCode implements UserDetailsService {

    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceCode.class);

    public UserDetailsServiceCode(UserRepository userRepository) {
    	logger.info("Initializing user repository.");
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {	
        Optional<User> user = userRepository.findByEmail(email);
        logger.info("Load the user by email from user repository.");
        if( !user.isPresent() ) {
        	logger.info("User details is not present in the repository.");
            throw new UsernameNotFoundException(email);
        }
        logger.info("User details is present in the repository.");
        return user.get();
    }
}
