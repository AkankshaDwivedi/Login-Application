# Spring Boot Login Application


# About

##Description : 
This is a demo project for login portal using Spring framework 5 and Spring Boot 2.
The idea is to build some basic login platform for USER and ADMIN role.
It is made using Spring Boot, Spring Security, Thymeleaf, Spring Data JPA.
Database is in memory H2.

##Functionality Covered:
There is a login and registration functionality included for users.
And an additional functionality to view all the users and delete them if required to the admin.
Every authenticated user can login the account and as per the access provided can view the admin page.
Non-authenticated users can view the main login page, wherein they need to register to view the authenticated resources.

##Configuration
###Configuration Files
src/resources/application.properties - main configuration file. 
This file includes the configuration for port, H2 database, datasource etc.

###How to run
Once the app starts, go to the web browser and visit http://localhost:8080

(1) Please use the already available data for user registration in the file : com.poc.loginapp.sampledata/DemoDataForRegistration
Run this file for creating user and admin roles and then continue on further functionality.

(2) for manual registration, register the user and admin. To assign the admin role to the user start the name of the admin with admin*, so that the framework can assign the role admin and grant admin access to the user.

##H2 Database web interface
Go to the web browser and visit http://localhost:8080/h2-console

In field JDBC URL put: jdbc:h2:mem:testdb
In /src/main/resources/application.properties file it is possible to change datasource url path. Please refer this file for further details.

##Spring Boot Testing
Please refer the section src/test/java for Spring Boot Testing.
Spring boot app testing is carried out using mockMvc.

##Code Coverage
Code Coverage for the Spring Boot Application is 82%.
Please find the details in 'index.html'.

