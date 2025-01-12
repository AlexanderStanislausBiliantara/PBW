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
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.WombatFm.RequiredRole;
import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Artist.ArtistService;
import com.example.WombatFm.Review.Review;
import com.example.WombatFm.Review.ReviewService;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowService;
import com.example.WombatFm.Song.Song;
import com.example.WombatFm.Song.SongService;

import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private SongService songService;

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
    public String showSetlist(HttpSession session,
            Model model,
            @PathVariable int showId,
            @RequestParam(name = "artist_id", required = false) Integer artistId) {
        if (artistId != null) {

            Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);

            if (setlist.isPresent()) {
                String userId = (String) session.getAttribute("user_id");

                // Render field comment only if user already is logged in
                if (userId != null) {
                    model.addAttribute("isLoggedIn", true);
                    model.addAttribute("userId", userId);

                    Review review = new Review();
                    review.setSetlistId(setlist.get().getSetlistId());
                    review.setUserId(Integer.parseInt(userId));
                    model.addAttribute("review", review);
                } else {
                    model.addAttribute("isLoggedIn", false);
                }

                // Show show = setlist.get().getShow();
                // List<Song> songs = setlist.get().getSongs();
                // model.addAttribute("title", show.getTitle());
                // model.addAttribute("location", show.getVenue());
                // model.addAttribute("date", show.getShowDate());
                // model.addAttribute("startTime", show.getStartTime());
                // model.addAttribute("duration", show.getDuration());
                // model.addAttribute("songs", songs);

                model.addAttribute("setlist", setlist.get());
                model.addAttribute("artistId", artistId);
                List<Review> reviews = reviewService.getReviewsByShowIdAndArtistId(showId, artistId);
                model.addAttribute("review", reviews);
                model.addAttribute("showId", showId);
                model.addAttribute("artistName", artistService.getArtistById(artistId).get().getName());
                return "SetlistArtist"; // setlist detail

            } else {
                // Setlist not found
                model.addAttribute("error", "Setlist not found");
                return "404";
            }
        } else {
            // artist on Setlist
            List<Artist> artists = showService.getShowArtists(showId);
            Show getShow = showService.getShowById(showId).get();
            model.addAttribute("showName", getShow.getTitle());
            model.addAttribute("showDate", getShow.getDateFormatted().toString());
            model.addAttribute("showId", showId);
            model.addAttribute("artists", artists);
            return "Setlist"; // show artist
        }
    }

    @RequiredRole({ "*" })
    @GetMapping("/add")
    public String addSetlist(Model model, SetlistForm setlistForm) {
        List<Show> shows = showService.getAllShows();
        List<Artist> artists = artistService.getAllArtists();

        model.addAttribute("shows", shows);
        model.addAttribute("artists", artists);
        return "AddSetlist";
    }

    @GetMapping("")
    public String showSetlist(Model model) {
        List<Setlist> topTenSetlists = this.setlistService.getTopSetlists(10);
        model.addAttribute("topTenSetlists", topTenSetlists);
        return "TopSetlists";
    }

    @RequiredRole({ "*" })
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

    // @PostMapping("/saveSetlist")
    // public String saveSetlist(@RequestParam(name="show") String show,
    // @RequestParam(name="artist") String artist){
    // Show saveShow = showService.getShowsByTitle(show).get(0);
    // Artist saveArtist = artistService.getArtistByName(artist).get();
    // setlistService.createSetlist(saveShow.getShowId(), saveArtist.getArtistId());
    // return "redirect:/addSetlist";
    // }

    @RequiredRole({ "*" })
    @GetMapping("/edit")
    public String editSetlist(HttpSession session,
            // @PathVariable String showIdStr,
            @RequestParam(name = "show_id", required = true) Integer showId,
            @RequestParam(name = "artist_id", required = true) Integer artistId,
            Model model) {
        // int showId = Integer.parseInt(showIdStr);

        // session.setAttribute("user_id", "7");
        String userId = (String) session.getAttribute("user_id").toString();

        if (userId == null)
            return "redirect:/login";
        else {
            model.addAttribute("showId", showId);
            model.addAttribute("artistId", artistId);

            Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);
            if (setlist.isPresent()) {
                Review review = new Review();

                review.setSetlistId(setlist.get().getSetlistId());

                review.setSongs(setlist.get().getSongs());

                review.setUserId(Integer.parseInt(userId));

                model.addAttribute("review", review);
                model.addAttribute("setlist", setlist.get());
                model.addAttribute("songs", songService.getAllSongs());

                return "EditSetlist";
            } else {
                model.addAttribute("error", "Setlist not found");
                return "404";
            }
        }
    }

    @RequiredRole({ "*" })
    @PostMapping("/{showIdStr}")
    public String addReview(
            @Valid Review review,
            BindingResult bindingResult,
            @PathVariable String showIdStr,
            @RequestParam(name = "artist_id", required = true) Integer artistId,
            Model model,
            HttpSession session) {

        // ? Should this method getUserId
        int showId = Integer.parseInt(showIdStr);
        String userId = (String) session.getAttribute("user_id");

        if (userId == null)
            return "redirect:/login";
        else {
            if (bindingResult.hasErrors()) {
                return "Setlist";
            }

            try {
                Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);
                if (setlist.isPresent()) {
                    reviewService.addReview(review);
                }
                return "redirect:/setlist/" + showId + "?artist_id=" + artistId;
            } catch (Exception e) {
                return "Setlist";
            }
        }

    }

    @RequiredRole({ "*" })
    @PostMapping("/edit")
    public String addRevision(
            @Valid Review review,
            BindingResult bindingResult,
            @RequestParam(name = "show_id", required = true) Integer showId,
            @RequestParam(name = "artist_id", required = true) Integer artistId,
            // @RequestParam(name = "song", required = true) String songStr,
            Model model,
            HttpSession session) {
        // System.out.println("songs size : " + (review.getSongs() == null ? "-" :
        // review.getSongs().size()));
        System.out.println("1 songs : " + review.getSongs());
        // System.out.println("song : " + songStr);
        // session.setAttribute("user_id", "7");
        String userId = (String) session.getAttribute("user_id");

        if (userId == null)
            return "redirect:/login";
        else {

            if (bindingResult.hasErrors()) {
                // System.out.println("binding error" + bindingResult.toString());

                Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);
                if (setlist.isPresent()) {

                    review.setSetlistId(setlist.get().getSetlistId());

                    review.setSongs(setlist.get().getSongs());

                    review.setUserId(Integer.parseInt(userId));
                    model.addAttribute("showId", showId);
                    model.addAttribute("artistId", artistId);
                    model.addAttribute("review", review);
                    model.addAttribute("setlist", setlist.get());
                    model.addAttribute("songs", songService.getAllSongs());
                }
                return "EditSetlist";
            }

            try {
                reviewService.addReview(review);
                return "redirect:/setlist/" + showId + "?artist_id=" + artistId;
            } catch (Exception e) {
                Optional<Setlist> setlist = setlistService.getNewestSetlist(showId, artistId);
                if (setlist.isPresent()) {
                    review.setSetlistId(setlist.get().getSetlistId());

                    review.setSongs(setlist.get().getSongs());

                    review.setUserId(Integer.parseInt(userId));

                    model.addAttribute("showId", showId);
                    model.addAttribute("artistId", artistId);
                    model.addAttribute("review", review);
                    model.addAttribute("setlist", setlist.get());
                    model.addAttribute("songs", songService.getAllSongs());

                }
                bindingResult.reject("InternalServerError", "An error occurred while creating new setlist");
                return "EditSetlist";
            }
        }
    }
}
