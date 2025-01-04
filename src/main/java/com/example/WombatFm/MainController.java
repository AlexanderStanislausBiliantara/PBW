package com.example.WombatFm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String index() {
        return "Main";
    }
  
    @GetMapping("/event")
    public String showEventPage() {
        return "Event";
    }

    @GetMapping("/search")
    public String searchShow() {
        return "Main";
    }
}
