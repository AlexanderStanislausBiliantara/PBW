package com.example.WombatFm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    @GetMapping
    public String index() {
        return "Main";
    }

    @GetMapping("/setlist")
    public String showSetlist() {
        return "Setlist";
    }

    @GetMapping("/artist")
    public String showArtistPage() {
        return "Artist";
    }

    @GetMapping("/event")
    public String showEventPage() {
        return "Event";
    }

    @GetMapping("/search")
    public String showSearchResults(@RequestParam String query, Model Model) {
        
    }
}
