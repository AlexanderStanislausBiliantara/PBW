package com.example.WombatFm.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SetlistController {

    @Autowired
    private SetlistService setlistService;

    @GetMapping("/setlist")
    public String showSetlist(Model model,
            @RequestParam(name = "show_id", required = false) String showId,
            @RequestParam(name = "artist_id", required = false) String artistId,
            @RequestParam(name = "version_id", required = false) String versionId) {

        if (showId != null && artistId != null) {

            setlistService.getAllSetlists();

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
        } else {
            // Homepage Setlist
        }

        return "Setlist";
    }

    @PostMapping("/setlist")
    public String addSetlist(@RequestBody Setlist setlist) {
        setlistService.addSetlist(setlist);
        return "redirect:/setlist";
    }

    @PutMapping("/setlist/{id}")
    public String updateSetlist(@PathVariable int id, @RequestBody Setlist setlistDetails) {
        setlistService.updateSetlist(id, setlistDetails);
        return "redirect:/setlist";
    }

    @DeleteMapping("/setlist/{id}")
    public String deleteSetlist(@PathVariable int id) {
        setlistService.deleteSetlist(id);
        return "redirect:/setlist";
    }
}
