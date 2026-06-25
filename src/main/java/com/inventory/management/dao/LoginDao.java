package com.inventory.management.dao;

import com.inventory.management.dto.LoginRequestDto;
import com.inventory.management.model.UserModel;

public interface LoginDao 
{

	String VALIDATE_LOGIN_CREDENTIALS = "SELECT user_id,first_name,last_name,user_name,country_code, phone_number,gender,role,date_of_birth,email, role,password_hash from users where (user_name=? OR email=? OR phone_number=? ) AND password_hash= ?";
	
	
	
	UserModel login(LoginRequestDto request);

	
	
}
