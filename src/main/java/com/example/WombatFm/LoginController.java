package com.example.WombatFm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WombatFm.User.User;
import com.example.WombatFm.User.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showHomepage(User user, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "Login";
        }
        return "redirect:";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "Login";
        }

        User user = userService.login(loginForm.getUsername(), loginForm.getPassword());
        if (user == null) {
            bindingResult.reject("userNotFound", "Please check username or password");
            return "Login";
        }

        session.setAttribute("user_id", "" + user.getUserId());

        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:";
    }
}
