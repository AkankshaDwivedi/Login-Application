package com.poc.loginapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poc.loginapp.model.User;
import com.poc.loginapp.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
   
    public UserService(UserRepository userRepository) {
    	logger.info("Initialize the user service constructor.");
        this.userRepository = userRepository;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        logger.info("Password encryption successfuly completed.");
        save(user);
        logger.info("User successfully saved in user repository.");
        return user;
    }

    public User save(User user) {
    	logger.info("Iniatiate saving the user in the repository.");
        return userRepository.save(user);
    }
}
