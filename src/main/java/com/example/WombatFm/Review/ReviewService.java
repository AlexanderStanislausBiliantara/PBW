package com.example.WombatFm.Review;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    public Optional<Review> getReviewById(int reviewId) {
        return reviewRepository.getReviewById(reviewId);
    }

    public List<Review> getReviewsByShowIdAndArtistId(int showId, int artistId) {
        return reviewRepository.getReviewsByShowIdAndArtistId(showId, artistId);
    }

}
