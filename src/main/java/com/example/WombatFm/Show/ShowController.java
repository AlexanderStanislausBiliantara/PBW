package com.example.WombatFm.Show;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.WombatFm.Gallery.Gallery;
import com.example.WombatFm.Gallery.GalleryService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;
    @Autowired
    private GalleryService galleryService;

    private static String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public String getTopShows(Model model){
        
        return "Show";
    }

    @GetMapping("/add")
    public String addShow(Show show) {
        return "AddShow";
    }

    @GetMapping("/search")
    public String searchShow(@RequestParam("query") String query, Model model) {
        List<Show> foundShows = this.showService.getShowsByTitle(query);
        model.addAttribute("shows", foundShows);
        model.addAttribute("query", query);
        return "search_for_show";
    }

    @GetMapping("/filtered_search")
    public String searchFilteredShow(@RequestParam("query") String query, @RequestParam("start_date") Date startDate, @RequestParam("end_date") Date endDate, @RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "size", required = false, defaultValue = "8") int size, Model model) {
        List<Show> foundShows = this.showService.getShowWithPagination(query, startDate, endDate, page, size);
        int pageCount = this.showService.getPageCount(query, startDate, endDate, size);
        
        model.addAttribute("currentPage", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("results", foundShows);
        return "search_for_show";
    }

    @PostMapping("/add")
    public String addShow(
            @Valid Show show,
            BindingResult bindingResult,
            // @RequestParam("showDate") String showDateStr,
            // @RequestParam("startTime") String startTimeStr,
            @RequestParam("images") MultipartFile[] images) {

        // System.out.println(show.toString());

        if (bindingResult.hasErrors()) {
            return "AddShow";
        }

        // if (images.length > 0) {
        // bindingResult.rejectValue("photoUrl", "imageNotFound", "Image is required");
        // return "AddShow";
        // }
        // if (showDateStr.isEmpty()) {
        // bindingResult.rejectValue("showDate", "showDateRequired", "Show date is
        // required");
        // return "AddShow";
        // }
        // if (startTimeStr.isEmpty()) {
        // bindingResult.rejectValue("startTime", "startTimeRequired", "Start time is
        // required");
        // return "AddShow";
        // }

        // try {
        // Date showDate = Date.valueOf(showDateStr);
        // show.setShowDate(showDate);

        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        // Time startTime = new Time(simpleDateFormat.parse(startTimeStr).getTime());

        // show.setStartTime(startTime);
        // } catch (Exception e) {
        // bindingResult.rejectValue("showDate", "error.show", "Invalid date or time
        // format");
        // return "AddShow";
        // }

        // System.out.println("add show");

        try {

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // -- create show --
            int idShow = showService.addShow(show);

            System.out.println("show created");

            // -- create gallery + save image --
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
            System.out.println("gallery created");

            if (rowsAffected == 0) {
                // return "AddShow"; // show must have at least one image
            }

            return "redirect:/setlist/" + idShow;
        } catch (Throwable e) {
            bindingResult.reject("InternalServerError", "An error occurred while adding the show.");
            return "AddShow";
        }
    }

    @GetMapping("/selectShow")
    public String selecdtShow(Model model){
        List<Show> allShow = showService.getAllShows();
        model.addAttribute("allShow", allShow);
        return "AddSetlist";
    }
}
