package com.example.WombatFm.Show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.WombatFm.Artist.Artist;

@Repository
public class JdbcShowRepository implements ShowRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Show> getAllShows() {
        String sql = "SELECT * FROM shows ";
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow);
        return results;
    }

    @Override
    public Optional<Show> getShowById(int showId) {
        String sql = "SELECT * FROM shows where show_id = ?";
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, showId);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public List<Show> getShowsByTitle(String showTitle) {
        String sql = "SELECT * FROM shows WHERE title LIKE ?";
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, "%" + showTitle + "%");
        return results;
    }

    @Override
    public List<Artist> getShowArtists(int showId) {
        String sql = "SELECT a.artist_id AS artist_id, a.name AS name, a.artist_photo_url AS artist_photo_url " +
                "FROM shows s " +
                "JOIN setlists se ON s.show_id = se.show_id " +
                "JOIN artists a ON se.artist_id = a.artist_id " +
                "WHERE s.show_id = ?";
        List<Artist> results = jdbcTemplate.query(sql, this::mapRowToArtist, showId);
        return results;
    }

    @Override
    public int addShow(Show show) {
        String sql = "INSERT INTO shows (title, venue, show_date, start_time, duration) VALUES (?, ?, ?, ?, ?)";
        int result = jdbcTemplate.update(sql, show.getTitle(), show.getVenue(), show.getShowDate(), show.getStartTime(),
                show.getDuration());
        return result;
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
                resultSet.getInt("show_id"),
                resultSet.getString("title"),
                resultSet.getString("venue"),
                resultSet.getDate("show_date"),
                resultSet.getTime("start_time"),
                resultSet.getInt("duration"));
    }

    private Artist mapRowToArtist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artist(
                resultSet.getInt("artist_id"),
                resultSet.getString("name"),
                resultSet.getString("artist_photo_url"));
    }
}
