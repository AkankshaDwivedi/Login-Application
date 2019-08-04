package com.poc.loginapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.NonNull;

@Entity
public class User implements UserDetails {

	@Id 
	@GeneratedValue
    private Long id;

    @NonNull
    @Size(min = 10, max = 30)
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @NotEmpty(message = "Please enter the First Name.")
    private String firstName;

    @NonNull
    @NotEmpty(message = "Please enter the Last Name.")
    private String lastName;
    
    @NonNull
    private String password;
    
    @NonNull
    private boolean enabled;

    @NonNull
    private String role;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public String getFullName(){
        return firstName + " " + lastName;
    }
	
	@Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        String ROLE_PREFIX = "ROLE_";
	        if (isEnabled()) {
	        	if(this.role == "ADMIN") {
	        		authList.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        	}  
        }
        return authList;
    }
}