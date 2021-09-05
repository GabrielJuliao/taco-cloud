package com.gabrieljuliao.tacocloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {
    @GetMapping("/SignIn")
    public String signIn(Model model, Error error) {
        model.addAttribute("errorMessage", "error: "+error.getMessage());
        return "signIn";
    }
}
