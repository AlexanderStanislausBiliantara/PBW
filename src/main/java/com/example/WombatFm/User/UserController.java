package com.example.WombatFm.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/showUsers")
    public String showUsers(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "size", required = false, defaultValue = "25") int size, Model model) {
        List<User> users = userService.getUsersWithPagination(page, size);
        int pageCount = this.userService.getUserPageCount(size);
        
        model.addAttribute("currentPageForUser", page);
        model.addAttribute("userPageCount", pageCount);
        model.addAttribute("foundUsers", users);
        return "AdminPage";
    }

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
