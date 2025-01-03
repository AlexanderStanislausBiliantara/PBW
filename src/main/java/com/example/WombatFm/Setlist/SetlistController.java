package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Artist.ArtistService;
import com.example.WombatFm.Review.Review;
import com.example.WombatFm.Review.ReviewService;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowService;
import com.example.WombatFm.Song.Song;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("/setlist")
public class SetlistController {

    @Autowired
    private SetlistService setlistService;

    @Autowired
    private ShowService showService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ReviewService reviewService;

    @Data
    @NoArgsConstructor
    class SetlistForm {
        private int showId;
        private int artistId;
    }

    // @GetMapping("test")
    // @ResponseBody
    // public String test() {
    // List<Review> result = reviewService.getAllReviews();
    // // List<Artist> result = showService.getShowArtists(1003);
    // if (result.size() > 0) {
    // String res = "";
    // for (Review review : result) {
    // res += review.toString() + " | ";
    // }
    // return res;
    // }
    // return "0";
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

                List<Review> reviews = reviewService.getReviewsByShowIdAndArtistId(showId, artistId);
                model.addAttribute("reviews", reviews);

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
    public String addSetlist(Model model, SetlistForm setlistForm) {
        List<Show> shows = showService.getAllShows();
        List<Artist> artists = artistService.getAllArtists();
        model.addAttribute("shows", shows);
        model.addAttribute("artists", artists);
        return "AddSetlist";
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

    @PostMapping("/add")
    public String addSetlist(@Valid SetlistForm setlistForm,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("shows", showService.getAllShows());
            model.addAttribute("artists", artistService.getAllArtists());
            return "AddSetlist";
        }

        try {
            setlistService.createSetlist(setlistForm.getShowId(),
                    setlistForm.getArtistId());
            return "redirect:/setlist";
        } catch (Throwable e) {
            bindingResult.reject("InternalServerError", "An error occurred while creating the setlist");
            model.addAttribute("shows", showService.getAllShows());
            model.addAttribute("artists", artistService.getAllArtists());
            return "AddSetlist";
        }
    }

    @PostMapping("/saveSetlist")
    public String saveSetlist(@RequestParam(name="show") String show, @RequestParam(name="artist") String artist){
        Show saveShow = showService.getShowsByTitle(show).get(0);
        Artist saveArtist = artistService.getArtistByName(artist).get();
        setlistService.createSetlist(saveShow.getShowId(), saveArtist.getArtistId());
        return "redirect:/addSetlist";
    }
}
