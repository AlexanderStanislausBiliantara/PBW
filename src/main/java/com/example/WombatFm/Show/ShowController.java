package com.example.WombatFm.Show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/add")
    public String addShow(Model model) {
        return "addShow";
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
