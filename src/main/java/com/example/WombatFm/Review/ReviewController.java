package com.example.WombatFm.Review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/chartData")
    public List<ReviewData> getReviewData() {
        return reviewService.getReviewsPerDay();
    }

    // @PostMapping
    // public String addReview(@Valid Review review,
    //     BindingResult bindingResult) {

    //     if (bindingResult.hasErrors()) {
    //         return "Setlist";
    //     }

    //     try {
    //         reviewService.addReview(review);
    //         return "redirect:/setlist" + "";
    //     } catch (Exception e) {
    //         return "Setlist";
    //     }
    // }

    // @PostMapping("/revision")
    // public String addRevision(@Valid Review review,
    //     BindingResult bindingResult) {

    //     if (bindingResult.hasErrors()) {
    //         return "EditSetlist";
    //     }

    //     try {
    //         reviewService.addRevision(review);
    //         return "redirect:/setlist" + "";
    //     } catch (Exception e) {
    //         return "EditSetlist";
    //     }
    // }
}
