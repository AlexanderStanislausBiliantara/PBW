package com.example.WombatFm.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    List<Review> getAllReviews();

    Optional<Review> getReviewById(int reviewId);

    List<Review> getReviewsByShowIdAndArtistId(int showId, int artistId);
}
