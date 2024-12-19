package com.example.WombatFm.Setlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class SetlistController {

    @GetMapping("/setlist")
    public String showSetlist(Model model ) {

        String songs[] = new String[6];

        songs[0] = "Attention";
        songs[1] = "Super Shy";
        songs[2] = "ETA";
        songs[3] = "OMG";
        songs[4] = "Ditto";

        model.addAttribute("title", "NewJeans World Tour");
        model.addAttribute("location", "Tokyo Dome, Japan");
        model.addAttribute("date", "January 5, 2024");
        model.addAttribute("songs", songs);
        return "Setlist";
    }
}
