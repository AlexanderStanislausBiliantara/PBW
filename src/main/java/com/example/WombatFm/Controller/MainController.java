package com.example.WombatFm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    @GetMapping("/")
    public String index() {
        return "Main";
    }

    @GetMapping("/artist")
    public String showArtistPage() {
        return "Artist";
    }

    @GetMapping("/event")
    public String showEventPage() {
        return "Event";
    }

    /* @GetMapping("/search")
    public String showSearchResults(@RequestParam String query, Model Model) {
        
    } */

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "Register";
    }

    @GetMapping("/addSetlist")
    public String addSetlist(){
        return "AddSetlist";
    }

    @GetMapping("/showSetlist")
    public String showSetlist(){
        return "Setlist";
    }
}
