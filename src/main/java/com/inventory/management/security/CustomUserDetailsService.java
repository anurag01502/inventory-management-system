package com.inventory.management.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.inventory.management.dao.UserDao;
import com.inventory.management.exception.CustomRuntimeException;
import com.inventory.management.model.UserModel;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    
    private final UserDao userDao;

    
    
    public CustomUserDetailsService(UserDao userDao) {
		super();
		this.userDao = userDao;
	}



	@Override
    public UserDetails loadUserByUsername(String identifier) throws CustomRuntimeException {

        UserModel user =userDao.findByIdentifier(identifier);

        if(user == null) {

            throw new CustomRuntimeException( "User not found",HttpStatus.NOT_FOUND);
        }

        return org.springframework.security
                .core.userdetails.User
                .builder()
                .username(user.getEmail())                
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}