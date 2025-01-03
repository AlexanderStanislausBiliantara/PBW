// package com.example.WombatFm.Review;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import jakarta.validation.Valid;

// @Controller
// @RequestMapping("/review")
// public class ReviewController {

// @Autowired
// private ReviewService reviewService;

// @PostMapping
// public String addReview(@Valid Review review,
// BindingResult bindingResult) {

// if (bindingResult.hasErrors()) {
// return "Setlist";
// }

// try {
// reviewService.addReview(review);
// return "redirect:/setlist" + "";
// } catch (Exception e) {
// return "Setlist";
// }
// }

// @PostMapping("/revision")
// public String addRevision(@Valid Review review,
// BindingResult bindingResult) {

// if (bindingResult.hasErrors()) {
// return "EditSetlist";
// }

// try {
// reviewService.addRevision(review);
// return "redirect:/setlist" + "";
// } catch (Exception e) {
// return "EditSetlist";
// }
// }
// }
