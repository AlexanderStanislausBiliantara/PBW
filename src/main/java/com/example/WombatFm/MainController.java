package com.example.WombatFm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Artist.ArtistService;
import com.example.WombatFm.Setlist.SetlistService;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowService;

@Controller
public class MainController {

    @Autowired
    private ShowService showService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SetlistService setlistService;

    @GetMapping("/")
    public String index() {
        return "Main";
    }

    /* @GetMapping("/artist")
    public String showArtistPage() {
        return "Artist";
    } */

    @GetMapping("/event")
    public String showEventPage() {
        return "Event";
    }

    @GetMapping("/search")
    public String searchShow() {
        return "Main";
    }

    @GetMapping("/addSetlist")
    public String addSetlist(Model model){
        List<Show> allShow = showService.getAllShows();
        List<Artist> allArtist = artistService.getAllArtists();
        model.addAttribute("allShow", allShow); 
        model.addAttribute("allArtists", allArtist);
        return "AddSetlist";
    }

    @GetMapping("/showSetlist")
    public String showSetlist(){
        return "Setlist";
    }

    @PostMapping("/saveSetlist")
    public String saveSetlist(@RequestParam(name="show") String show, @RequestParam(name="artist") String artist){
        Show saveShow = showService.getShowsByTitle(show).get(0);
        Artist saveArtist = artistService.getArtistByName(artist).get();
        setlistService.createSetlist(saveShow.getShowId(), saveArtist.getArtistId());
        return "redirect:/addSetlist";
    }
}
