package com.example.WombatFm.Artist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.WombatFm.RequiredRole;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ShowService showService;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/src/main/resources/static/uploads/";

    @GetMapping
    public String showArtistPage(Model model) {
        List<Artist> artists = artistService.getAllArtists();

        System.out.println("Artists: " + artists);
        model.addAttribute("artists", artists.subList(0, 18));
        return "Artist";
    }

    @GetMapping("/{id}")
    public String getArtist(@PathVariable int id, Model model) {
        Optional<Artist> foundArtist = artistService.getArtistById(id);

        if(foundArtist.isPresent()) {
            List<Show> artistShows = showService.getShowsByArtistId(id);
            Artist artist = new Artist();
            artist = foundArtist.get();

            model.addAttribute("artistphoto", artist.getPhotoUrl());
            model.addAttribute("artistname", artist.getName());
            model.addAttribute("artistshows", artistShows);
            return "ArtistPage";
        }else{
            model.addAttribute("error", "Setlist not found");
            return "404";
        }
    }

    @RequiredRole({ "*" })
    @GetMapping("/add")
    public String showAddArtistForm(Artist artist) {
        return "AddArtist";
    }

    @RequiredRole({ "*" })
    @PostMapping("/add")
    public String addArtist(
            @Valid Artist artist,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image) {
        if (bindingResult.hasErrors()) {
            return "AddArtist";
        }

        if (image.isEmpty()) {
            bindingResult.rejectValue("photoUrl", "imageNotFound", "Image is required");
            return "AddArtist";
        }

        String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path path = Paths.get(UPLOAD_DIR).resolve(filename);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Throwable e) {
            bindingResult.rejectValue("photoUrl", "uploadError", "Failed to upload image. Please try again.");
            return "AddArtist";
        }
        System.out.println("added image");

        try {
            Artist newArtist = new Artist(0, artist.getName(), filename);
            int idArtist = artistService.addArtist(newArtist);

            return "redirect:/artist/" + idArtist;
        } catch (Throwable e) {
            bindingResult.reject("InternalServerError", "Failed to add artist. Please try again.");
            return "AddArtist";
        }
    }
}
