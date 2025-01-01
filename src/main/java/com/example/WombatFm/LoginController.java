package com.example.WombatFm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showHomepage(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if(username == null) {
            return "Login";
        }
        return "Main";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpSession session) {
        User user = userService.login(username, password);
        if(user == null) {
            return "Login";
        }

        session.setAttribute("username", username);
        session.setAttribute("password", password);
        session.setAttribute("role", user.getRole());
        return "Main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
