package com.poc.loginapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poc.loginapp.model.User;
import com.poc.loginapp.repository.UserRepository;
import com.poc.loginapp.service.UserService;

@Controller
public class UserController {
	    
	    @Autowired
	    private UserRepository userRepository;
	    private UserService userService;
	    private final Logger logger = LoggerFactory.getLogger(UserController.class);
	    
	    public UserController(UserService userService) {
	        this.userService = userService;    
	    }

	    @GetMapping("/")
	    public String home(Model model) {
	    	logger.info("Redirected to Home link.");
	        return "home";
	    }
	    
	    @GetMapping("/login")
	    public String login() {
	    	logger.info("Redirected to Login link.");
	        return "login";
	    }
 
	    @GetMapping("/admin")
	    public String getAllUsers(Model model, Authentication authentication) {
	    	logger.info("Redirected to Admin link.");
	    	model.addAttribute("user", userRepository.findAll());
	    	logger.info("Details of all the existig user successfully added to the model.");
	    	 return "admin";
	    }

	    @RequestMapping("/delete/{id}")    
	    public String delete(@PathVariable Long id){    
	    	logger.info("Redirected to Delete link.");
	        userRepository.deleteById(id);
	        logger.info("User with 'id' " + id + " is successfully deleted."); 
	        return "redirect:/admin";    
	    }     
	    
	    @GetMapping("/register")
	    public String register(Model model) {
	    	logger.info("Redirected to Register link.");
	        model.addAttribute("user",new User());
	        logger.info("Details of new user successfully added to the model.");
	        return "register";
	    }

	    @PostMapping("/register")
	    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
	        if( bindingResult.hasErrors() ) {
	            logger.info("New user cannot be registerd due to validation errors.");
	            model.addAttribute("user",user);
	            model.addAttribute("validationErrors", bindingResult.getAllErrors());
	            return "register";
	        } else {
	            // Register new user
	        	Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
	            if(existingUser.isPresent())
	            {
	            	logger.info("This email already exists.");
	                model.addAttribute("message","This email already exists! Please sign in or register with a different email ID.");
	                return "register";
	            }
	            logger.info("Registering a new user");
	            User newUser = userService.register(user);
	            redirectAttributes
	                    .addAttribute("id",newUser.getId())
	                    .addFlashAttribute("success",true)
	            		.addFlashAttribute("fullName", newUser.getFullName());
	            newUser.setEnabled(true);
	            // For configuring the user as ADMIN
	            if (newUser.getFirstName().startsWith("admin")) {
	            	newUser.setRole("ADMIN");
	            }
	            logger.info("New user registered successfully.");
	            userService.save(newUser);
	            return "redirect:/register";
	        }
	    }
	}