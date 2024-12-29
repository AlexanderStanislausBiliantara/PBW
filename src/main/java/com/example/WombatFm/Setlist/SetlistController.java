package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Artist.ArtistService;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowService;
import com.example.WombatFm.Song.Song;

@Controller
@RequestMapping("/setlist")
public class SetlistController {

    @Autowired
    private SetlistService setlistService;

    @Autowired
    private ShowService showService;

    @Autowired
    private ArtistService artistService;

    // @Autowired
    // private SongService songService;

    // @GetMapping("test")
    // @ResponseBody
    // public String test() {
    // Optional<Song> result = songService.getSongById(11);
    // // List<Artist> result = showService.getShowArtists(1003);
    // String res = "";
    // // for (Artist artist : result) {
    // // res += artist.getName() + " - ";
    // // }
    // // if (result.size() > 0) {
    // // return result.get().getSongs().size() + "";
    // // }
    // return result.get().getTitle();
    // }

    @GetMapping("/{showId}")
    public String showSetlist(Model model,
            @PathVariable int showId,
            @RequestParam(name = "artist_id", required = false) Integer artistId) {
        if (artistId != null) {

            Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);

            if (setlist.isPresent()) {
                Show show = setlist.get().getShow();
                List<Song> songs = setlist.get().getSongs();
                model.addAttribute("title", show.getTitle());
                model.addAttribute("location", show.getVenue());
                model.addAttribute("date", show.getShowDate());
                model.addAttribute("startTime", show.getStartTime());
                model.addAttribute("duration", show.getDuration());
                model.addAttribute("songs", songs);
                return "Setlist"; // setlist detail

            } else {
                // Setlist not found
                model.addAttribute("error", "Setlist not found");
                return "404";
            }
        } else {
            // artist on Setlist
            List<Artist> artists = showService.getShowArtists(showId);
            model.addAttribute("artists", artists);
            return "Setlist"; // show artist
        }
    }

    @GetMapping("/add")
    public String addSetlist(Model model) {
        List<Show> shows = showService.getAllShows();
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("shows", shows);
        model.addAttribute("artists", artists);
        return "AddSetlist";
    }

    @PostMapping("/add")
    public String addSetlist(@RequestParam("showId") int showId, @RequestParam("artistId") int artistId) {
        setlistService.createSetlist(showId, artistId);
        return "redirect:/setlist";
    }
}
