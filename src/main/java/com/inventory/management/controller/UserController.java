package com.inventory.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.inventory.management.dto.ProfileResponseDto;
import com.inventory.management.dto.UpdatePasswordRequestDto;
import com.inventory.management.dto.UserRequestDto;
import com.inventory.management.exception.CustomRuntimeException;
import com.inventory.management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/view-profile")
    public ResponseEntity<ProfileResponseDto> viewProfile(Authentication authentication) 
	{
        String identifier = authentication.getName();
        ProfileResponseDto profile =  userService.findByIdentifier(identifier);
        return ResponseEntity.ok(profile);
    }
	
	@PutMapping("/update-profile")
	public ResponseEntity<String> updateProfile(@RequestBody UserRequestDto dto,Authentication authentication) {

	   Integer status=  userService.updateUserProfile(dto,authentication.getName());   
	   if(status==0)
	   {
		   throw new CustomRuntimeException("User Does Not Exist!", HttpStatus.NOT_FOUND);
	   }
	    return ResponseEntity.ok("Your profile is updated!");
	}
	
	@DeleteMapping("/delete-profile")
	public ResponseEntity<String> deleteProfile(Authentication authentication)
	{
		String emailAsIdentifier=  authentication.getName();
		userService.deleteProfile(emailAsIdentifier);
		return ResponseEntity.ok("Account Deleted Successfully!");
	}
	
	@PutMapping("/update-password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequestDto updatePasswordRequest  ,Authentication authentication) {
		
		String emailAsIdentifier=  authentication.getName();
		userService.updatePassword(updatePasswordRequest, emailAsIdentifier);
		return ResponseEntity.ok("Password Changed!");
	}

	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new CustomRuntimeException(
	                "Invalid token",HttpStatus.BAD_REQUEST);
	    }
	    String token = authHeader.substring(7);
	    userService.logout(token);
	    return ResponseEntity.ok("Logged out successfully");
	}
	
	

}