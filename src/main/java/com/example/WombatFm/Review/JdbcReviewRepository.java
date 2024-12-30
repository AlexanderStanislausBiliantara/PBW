package com.example.WombatFm.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.WombatFm.Song.Song;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> getAllReviews() {
        String sql = "SELECT review_id, user_id, setlist_id, comment, created_at FROM reviews ";
        List<Review> results = jdbcTemplate.query(sql, this::mapRowToReview);

        return results;
    }

    @Override
    public Optional<Review> getReviewById(int reviewId) {
        String sql = "SELECT review_id, user_id, setlist_id, comment, created_at FROM reviews WHERE review_id = ?";
        List<Review> results = jdbcTemplate.query(sql, this::mapRowToReview, reviewId);
        if (results.isEmpty()) {
            return Optional.empty();
        }
        Review review = results.get(0);
        review.setSongs(getSongsForReview(review.getReviewId()));
        return Optional.of(review);
    }

    @Override
    public List<Review> getReviewsByShowIdAndArtistId(int showId, int artistId) {
        // TODO: Optimize n+1 query prob

        String sql = "SELECT r.review_id AS review_id, r.user_id AS user_id, " +
                "r.setlist_id AS setlist_id, r.comment AS comment, r.created_at AS created_at " +
                "FROM shows s " +
                "JOIN setlists se ON s.show_id = se.show_id " +
                "JOIN artists a ON se.artist_id = a.artist_id " +
                "JOIN reviews r ON se.setlist_id = r.setlist_id " +
                "WHERE s.show_id = ? AND se.artist_id = ?";
        List<Review> reviews = jdbcTemplate.query(sql, this::mapRowToReview, showId, artistId);

        for (Review review : reviews) {
            review.setSongs(getSongsForReview(review.getReviewId()));
        }
        return reviews;
    }

    private List<Song> getSongsForReview(int reviewId) {
        String sql = "SELECT si.song_id, so.title, si.song_order, so.artist_id AS artist_id  "
                + "FROM setlist_version sv "
                + "JOIN setlist_items si ON sv.version_id = si.version_id "
                + "JOIN songs so ON si.song_id = so.song_id "
                + "WHERE sv.review_id = ? "
                + "ORDER BY si.song_order";
        return jdbcTemplate.query(sql, this::mapRowToSong, reviewId);
    }

    private Review mapRowToReview(ResultSet resultSet, int rowNum) throws SQLException {
        return new Review(
                resultSet.getInt("review_id"),
                resultSet.getInt("user_id"),
                resultSet.getInt("setlist_id"),
                resultSet.getString("comment"), resultSet.getDate("created_at"));
    }

    private Song mapRowToSong(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song(
                resultSet.getInt("song_id"),
                resultSet.getString("title"),
                resultSet.getInt("artist_id"));
        song.setSongOrder(resultSet.getInt("song_order"));
        return song;
    }
}
