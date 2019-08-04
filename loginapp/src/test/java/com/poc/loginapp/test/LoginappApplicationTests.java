package com.poc.loginapp.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.poc.loginapp.controller.UserController;
import com.poc.loginapp.model.User;
import com.poc.loginapp.repository.UserRepository;
import com.poc.loginapp.security.UserDetailsServiceCode;
import com.poc.loginapp.service.UserService;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginappApplicationTests {
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserController controller;
	
	@InjectMocks
	private UserService userService;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		
		ReflectionTestUtils.setField(controller, "userRepository", userRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();	
	}

	@Test
	public void testLogin() throws Exception {
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/login.html"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.forwardedUrl("login"));
	}
	
	@Test
	public void testAdmin() throws Exception {
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/admin.html"))
			.andExpect(MockMvcResultMatchers.status().isOk()) 
			.andExpect(MockMvcResultMatchers.model().attribute("user", userRepository.findAll()))
			.andExpect(MockMvcResultMatchers.forwardedUrl("admin"));
		
		verify(userRepository, times(2)).findAll();
	}

	@Test
	public void testRegisterGet() throws Exception {
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/register.html"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("register"));
	}
	
	@Test
	public void testRegisterPostNegative() throws Exception {
		
		 this.mockMvc.perform(MockMvcRequestBuilders.post("/register.html")
		            .param("firstName", "<error>")
		            .param("lastName", "<error>")
		            .param("email", "<error>"))
		            .andExpect(MockMvcResultMatchers.status().isOk())
		            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
					.andExpect(MockMvcResultMatchers.model().attributeExists("validationErrors"))
		            .andExpect(MockMvcResultMatchers.forwardedUrl("register"));
	}
		
	@Test
	public void testRegisterUser() throws Exception {
		
		UserService userService = new UserService(userRepository);
		
		User user = new User();
        user.setId(1L);
        user.setFirstName("DemoName");
        user.setLastName("DemoLatName");
        user.setEmail("demo@gmail.com");
        user.setPassword("password123");

        userService.register(user);
        verify(userRepository, times(1)).save(user);
	}

	@Test
	public void testHome() throws Exception {
		
	mockMvc
			.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.forwardedUrl("home"));
	}
	
	@Test
	public void testDelete() throws Exception {
		
		Long id;
		id = 1L;
		mockMvc
			.perform(MockMvcRequestBuilders.get("/delete/{id}", id))
			.andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
		
		verify(userRepository, times(1)).deleteById(id);
	}
	
	@Test
	public void testLoadUserByUsername(){
		
		UserDetailsServiceCode userDetailsServiceCode = new UserDetailsServiceCode(userRepository);
		String email = "demo@gmail.com";
		try {
			userDetailsServiceCode.loadUserByUsername(email);
		}
		catch (Exception e) {
		      e.printStackTrace();
		}
		verify(userRepository, times(1)).findByEmail(email);	
	}
}