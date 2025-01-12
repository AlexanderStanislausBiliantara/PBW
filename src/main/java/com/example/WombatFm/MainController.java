package com.example.WombatFm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.WombatFm.Setlist.SetlistService;
import com.example.WombatFm.Show.ShowService;

@Controller
public class MainController {

    @Autowired
    SetlistService setlistService;
    @Autowired
    ShowService showService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("setlists", setlistService.getTopSetlists(5));
        model.addAttribute("shows", showService.getAllShows().subList(0, 5));

        return "Main";
    }

    @GetMapping("/search")
    public String searchShow() {
        return "Main";
    }
}
