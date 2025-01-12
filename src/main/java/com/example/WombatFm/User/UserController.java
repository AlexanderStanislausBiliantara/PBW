package com.example.WombatFm.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WombatFm.RequiredRole;
import com.example.WombatFm.Review.Review;
import com.example.WombatFm.Review.ReviewService;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/admin")
    public String adminDashboard(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "25") int size, Model model) {
        List<User> users = userService.getUsersWithPagination(page, size);
        int pageCount = this.userService.getUserPageCount(size);

        List<Review> reviews = reviewService.getAllReviews();

        model.addAttribute("currentPageForUser", page);
        model.addAttribute("userPageCount", pageCount);
        model.addAttribute("foundUsers", users);

        model.addAttribute("reviews", reviews);
        return "AdminPage";
    }

    @GetMapping("/register")
    public String registerView(User user) {
        return "Register";
    }

    @PostMapping("/validate")
    public String register(@Valid User user, BindingResult bindingResult) {
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            bindingResult.rejectValue("confirmPassword", "PasswordMismatch", "Passwords doesn't match");
        }

        if (bindingResult.hasErrors()) {
            return "Register";
        }

        boolean registerSuccess = this.userService.register(user);
        if (!registerSuccess) {
            bindingResult.rejectValue("username", "DuplicateValue", "Username is already taken");
            return "Register";
        }

        return "redirect:/login";
    }

    @RequiredRole({ "*" })
    @PostMapping("/saveUserRole")
    public String saveUserRole(@RequestParam(name = "uid") int userId, @RequestParam(name = "user_role") String role,
            @RequestParam(name = "user_activity") boolean isActive) {
        this.userService.updateUserRow(userId, role, isActive);
        return "redirect:/showUsers";
    }

    // @PostMapping("/saveRoles")
    // public String saveRoles(@RequestParam(name = "roles") Map<String, String>
    // roles) {
    // Map<Integer, String> userRoles = new HashMap<>();

    // for(Map.Entry<String, String> entry : roles.entrySet()) {
    // int userId = Integer.parseInt(entry.getKey());
    // String role = entry.getValue();
    // userRoles.put(userId, role);
    // }
    // userService.updateMultipleUserRole(userRoles);
    // return "redirect:/showUsers";
    // }
}
