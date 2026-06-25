package com.inventory.management.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.management.dto.LoginRequestDto;
import com.inventory.management.dto.RegistrationRequestDto;
import com.inventory.management.service.LoginService;
import com.inventory.management.service.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/users")
public class AuthController {

	Logger logger  = LoggerFactory.getLogger(AuthController.class);

    private final RegistrationService registrationService;
	private final LoginService loginService;
   
    
    public AuthController(RegistrationService registrationService,LoginService loginService)
    {
    	this.registrationService =registrationService;
    	this.loginService=loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register( @Valid @RequestBody RegistrationRequestDto request) {

    	
    	logger.info("register controller reached!");
        registrationService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User Registered Successfully");
    }
    
	@PostMapping("/login")
	public ResponseEntity<Map<Object,String>> login(@Valid @RequestBody LoginRequestDto loginRequest,HttpServletRequest request) {
		
		
		
		return ResponseEntity.ok(loginService.login(loginRequest));
	}
}