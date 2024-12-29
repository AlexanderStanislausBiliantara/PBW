package com.example.WombatFm.Artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/add")
    public String showAddArtistForm(Model model) {
        return "addArtist";
    }

    @PostMapping("/add")
    public String addArtist(@RequestBody Artist artist) {
        try {
            int idArtist = artistService.addArtist(artist);
            return "redirect:/setlist/" + idArtist;
        } catch (Exception e) {
            return "redirect:/artist/add";
        }
    }
}
