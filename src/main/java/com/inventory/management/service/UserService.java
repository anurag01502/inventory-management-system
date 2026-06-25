package com.inventory.management.service;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inventory.management.dao.UserDao;
import com.inventory.management.dto.ProfileResponseDto;
import com.inventory.management.dto.UpdatePasswordRequestDto;
import com.inventory.management.dto.UserRequestDto;
import com.inventory.management.exception.CustomRuntimeException;
import com.inventory.management.model.UserModel;
import com.inventory.management.rowmapper.UserRowMapper;


@Service
public class UserService {

	
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	public UserService(UserDao userDao,PasswordEncoder passwordEncoder) {
		super();
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;

	}

	public ProfileResponseDto findByIdentifier(String identifier) {

		UserModel model = userDao.findByIdentifier(identifier);	
		ProfileResponseDto profileResponse = UserRowMapper.toDto(model);
		
		return profileResponse;
	}

	@Transactional
	public Integer updateUserProfile(UserRequestDto userRequestDto,String email){
		
		return userDao.updateUserProfile(UserRowMapper.toModel(userRequestDto),email);
		
	}



	@Transactional
	public void deleteProfile(String email) {
		userDao.deleteProfile(email);
		
	}



	@Transactional
	public void updatePassword(UpdatePasswordRequestDto updatePasswordRequestDto,String email) {
		
		UserModel userModel=   userDao.findByIdentifier(email);
		String oldPasswordHash = userModel.getPassword();
		String oldPasswordRequest = updatePasswordRequestDto.getOldPassword();
		 boolean isPasswordMatches= passwordEncoder.matches( oldPasswordRequest,oldPasswordHash);
		if(!isPasswordMatches)
		{
			throw new CustomRuntimeException("In correct Old password", HttpStatus.FORBIDDEN);
		}
		
		String newPassword = updatePasswordRequestDto.getNewPassword();
		String confirmNewPassword = updatePasswordRequestDto.getConfirmNewPassword();
		if(!newPassword.equals(confirmNewPassword))
		{
			throw new CustomRuntimeException("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
		}
		 newPassword = passwordEncoder.encode(updatePasswordRequestDto.getNewPassword());
		
		
		userDao.updatePassword(newPassword, email);
		
	}

	@Transactional
	public void logout(String tokenId) {
	
		
		userDao.blackListToken(tokenId);
		
	}

	public Boolean isBlackListed(String tokenId) {
		
		return userDao.isBlackListed(tokenId);
	}



}
