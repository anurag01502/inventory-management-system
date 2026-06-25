package com.inventory.management.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

import com.inventory.management.dao.LoginDao;
import com.inventory.management.dto.LoginRequestDto;
import com.inventory.management.security.JwtUtil;

@Service
public class LoginService  {

	private final LoginDao loginDao;
	private final JwtUtil jwtUtil;
	public LoginService(LoginDao loginDao,JwtUtil jwtUtil)
	{
		this.loginDao=loginDao;
		this.jwtUtil=jwtUtil;
		
	}
	
	public Map<Object, String> login(LoginRequestDto loginRequest) {
		
		
		Map<Object, String> loginResponse = new ConcurrentHashMap<>();
		
		loginDao.login(loginRequest);		
		String token = jwtUtil.generateToken(loginRequest.getIdentifier());
		
		loginResponse.put("message", "Successfully LoggedIn!");
		loginResponse.put("token", token);
		return loginResponse;
	}

}
