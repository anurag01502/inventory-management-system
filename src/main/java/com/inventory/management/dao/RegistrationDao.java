package com.inventory.management.dao;

import com.inventory.management.dto.RegistrationRequestDto;

public interface RegistrationDao {

     static final String INSERT_USER = """
        INSERT INTO users
        (
            first_name,
            last_name,
            user_name,
            email,
            country_code,
            phone_number,
            date_of_birth,
            gender,
            role,
            password_hash
        )
        VALUES
        (
            ?, ?, ?, ?, ?, ?, ?, ?,?,?
        )
        """;
    
    
     static final String CHECK_USER_EXISTS = """
SELECT COUNT(*) FROM users WHERE user_name = ?OR email = ?""";
    void register(RegistrationRequestDto request);
    
    public boolean userExists(String username,String email);

}
