package com.example.WombatFm.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerView(User user) {
        return "Register";
    }

    @PostMapping("/validate")
    public String register(@Valid User user, BindingResult bindingResult) {
        if(!user.getConfirmPassword().equals(user.getPassword())) {
            bindingResult.rejectValue("confirmpassword", "PasswordMismatch", "Passwords do not match");
        }

        if(bindingResult.hasErrors()) {
            return "Register";
        }

        boolean registerSuccess = this.userService.register(user);
        if(!registerSuccess) {
            bindingResult.rejectValue("username", "DuplicateValue", "Username is already taken");
            return "Register";
        }

        return "redirect:/login";
    }
}
