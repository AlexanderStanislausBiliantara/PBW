package com.example.WombatFm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MainController {
    
    @GetMapping
    public String index() {
        return "Main";
    }
}
