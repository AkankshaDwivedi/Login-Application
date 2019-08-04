package com.poc.loginapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
	
    private UserDetailsServiceCode userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(Security.class);

    public Security(UserDetailsServiceCode userDetailsService) {
    	logger.info("Initializing user service..");
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	logger.info("Configure the HttpSecurity.");
        http
           .authorizeRequests()
                .antMatchers("/").permitAll()
           		.antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .and()
           .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
           .logout()
                .and()
           .csrf().disable()
           .headers().frameOptions().disable();
        logger.info("HttpSecurity configured successfully.");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	logger.info("Dao Authentication.");
        auth.userDetailsService(userDetailsService);
    }
}
