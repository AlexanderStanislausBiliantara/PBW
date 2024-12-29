package com.example.WombatFm.Show;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.WombatFm.Gallery.Gallery;
import com.example.WombatFm.Gallery.GalleryService;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;
    @Autowired
    private GalleryService galleryService;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping("/add")
    public String addShow(Model model) {
        return "AddShow";
    }

    @PostMapping("/add")
    public String addShow(
            @RequestParam("title") String title,
            @RequestParam("venue") String venue,
            @RequestParam("showDate") Date showDate,
            @RequestParam("startTime") String startTimeStr,
            @RequestParam("duration") int duration,
            @RequestParam("images") MultipartFile[] images) {

        if (title == null || title.isEmpty() || venue == null || venue.isEmpty() || showDate == null
                || startTimeStr == null || startTimeStr.isEmpty() || duration == 0 || images == null
                || images.length == 0) {
            return "redirect:/show/add";
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Time startTime = new Time(simpleDateFormat.parse(startTimeStr).getTime());

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Show show = new Show(0, title, venue, showDate, startTime, duration);
            int idShow = showService.addShow(show);

            List<Gallery> galleries = new ArrayList<>();
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {

                    String filename = UUID.randomUUID().toString() + "_" +
                            image.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR).resolve(filename);
                    Files.copy(image.getInputStream(), path,
                            StandardCopyOption.REPLACE_EXISTING);

                    galleries.add(new Gallery(0, idShow, filename));
                }
            }

            int rowsAffected = galleryService.addShowImages(galleries);

            if (rowsAffected == 0) {
                return "redirect:/show/add";
            }

            return "redirect:/setlist/" + idShow;
        } catch (Exception e) {
            return "redirect:/show/add";
        }
    }
}
