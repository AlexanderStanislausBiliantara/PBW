package com.example.WombatFm.Song;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.WombatFm.Artist.ArtistService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private ArtistService artistService;

    @GetMapping("/{songId}")
    public String songDetailPage(
            Model model,
            @PathVariable int songId) {

        Optional<Song> song = songService.getSongById(songId);

        if (song.isPresent()) {
            model.addAttribute("song", song.get());
            return "SongDetail";
        } else {
            return "404";
        }
    }

    @GetMapping("/add")
    public String addSongPage(Song song) {
        return "AddSong";
    }

    @PostMapping("/add")
    public String addSong(
            @Valid Song song,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "AddSong";
        }

        if (artistService.getArtistById(song.getArtistId()).isEmpty()) {
            bindingResult.rejectValue("artistId", "ArtistNotFound", "Artist not found");
            return "AddSong";
        }

        try {
            int songId = songService.addSong(song);

            return "redirect:/song/" + songId;
        } catch (Throwable e) {
            bindingResult.reject("InternalServerError", "An error occurred while adding the song.");
            return "AddSong";
        }
    }
}
