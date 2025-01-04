package com.example.WombatFm.Artist;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.WombatFm.RequiredRole;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public String showArtistPage() {
        return "Artist";
    }

    @GetMapping("/{id}")
    public String getArtist(@PathVariable int id) {
        return "ArtistPage";
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
