package com.gabrieljuliao.tacocloud.model;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class SignUpForm {
    private String username;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public User toUser(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new User(username, bCryptPasswordEncoder.encode(password),
                fullName, street, city, state, zip, phoneNumber);
    }
}
