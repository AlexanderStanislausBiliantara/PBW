package com.example.WombatFm.Show;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/add")
    public String addShow(Model model) {
        return "addShow";
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
    @ResponseBody
    public String addShow(@RequestBody Show show) {
        try {
            showService.addShow(show);
            return "redirect:/show";
        } catch (Exception e) {
            return "redirect:/show/add";
        }
    }
}
