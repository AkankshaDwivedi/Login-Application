package com.poc.loginapp.sampledata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.poc.loginapp.model.User;
import com.poc.loginapp.service.UserService;

@Component
public class DemoDataForRegistration implements CommandLineRunner{
	
	private final Logger logger = LoggerFactory.getLogger(DemoDataForRegistration.class);
    private UserService userService;

    public DemoDataForRegistration(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        addUsersAndRoles();
    }

    private void addUsersAndRoles() {
        logger.info("Create user and set the credentials.");
	    User user = new User();
	    user.setEmail("user@user.com");
	    user.setFirstName("user");
	    user.setLastName("user");
	    user.setId(1L);
	    user.setPassword("123");
	    logger.info("Credentials for user created successfully.");
	    registerUser(user); 
	    
	    logger.info("Create admin and set the credentials.");
	    User admin = new User();
	    admin.setEmail("admin@admin.com");
	    admin.setFirstName("admin");
	    admin.setLastName("admin");
	    admin.setId(2L);
	    admin.setPassword("123");
	    logger.info("Credentials for admin created successfully.");
	    registerUser(admin); 
	    }
    
    private void registerUser(User user) {
    	userService.register(user);
	    logger.info("User registered successfully.");
        user.setEnabled(true);
        // For configuring the user as ADMIN
        if (user.getFirstName().startsWith("admin")) {
        	user.setRole("ADMIN");
        }
        userService.save(user);
    }
}
