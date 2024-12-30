package com.example.WombatFm.Song;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

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
    public String addSongPage(Model model) {
        return "AddSong";
    }

    @PostMapping("/add")
    public String addSong(
            @RequestParam("artistId") int artistId,
            @RequestParam("title") String title) {

        try {
            Song newSong = new Song(0, title, artistId);
            int songId = songService.addSong(newSong);

            return "redirect:/song/" + songId;
        } catch (Exception e) {
            return "redirect:/song/add";
        }
    }
}
