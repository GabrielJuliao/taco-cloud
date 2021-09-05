package com.gabrieljuliao.tacocloud.controllers;

import com.gabrieljuliao.tacocloud.model.SignUpForm;
import com.gabrieljuliao.tacocloud.model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SignUp")
public class SignUpController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignUpController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String signUpForm() {
        return "signUp";
    }

    @PostMapping
    public String processSignUp(SignUpForm signUpForm) {

        userRepository.save(signUpForm.toUser(bCryptPasswordEncoder));
        return "redirect:/SignIn";
    }
}
