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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.model.Model;

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

    @GetMapping("/add")
    public String showAddArtistForm(Model model) {
        return "AddArtist";
    }

    @PostMapping("/add")
    public String addArtist(@RequestParam("name") String name,
            @RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return "redirect:/artist/add";
        }

        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR).resolve(filename);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(path.toString());

            Artist artist = new Artist(0, name, path.toString());
            int idArtist = artistService.addArtist(artist);

            return "redirect:/artist/" + idArtist;
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/artist/add";
        }
    }
}
