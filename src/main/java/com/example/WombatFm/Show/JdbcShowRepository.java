package com.example.WombatFm.Show;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public List<Show> getShowsByTitle(String showTitle, int limit, int offset) {
        String sql = """
                SELECT * 
                FROM 
                    shows 
                WHERE 
                    title ILIKE CONCAT('%', ?, '%')
                ORDER BY
                    show_date ASC LIMIT ? OFFSET ?
                """;
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, showTitle, limit, offset);
        return results;
    }

    @Override
    public int countShows(String showTitle) {
        String sql = """
                SELECT 
                    COUNT(*)
                FROM 
                    shows 
                WHERE 
                    title ILIKE CONCAT('%', ?, '%')
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, showTitle);
    }

    @Override
    public List<Show> getFilteredShows(String showTitle, Date startDate, Date endDate, int limit, int offset) {
        String sql = """
                SELECT * 
                FROM 
                    shows
                WHERE
                    title ILIKE CONCAT('%', ?, '%') AND show_date BETWEEN ? AND ?
                ORDER BY
                    show_date ASC LIMIT ? OFFSET ?
                """;
        return jdbcTemplate.query(sql, this::mapRowToShow, showTitle, startDate, endDate, limit, offset);
    }

    @Override
    public int countFilteredShows(String showTitle, Date startDate, Date endDate) {
        String sql = """
                SELECT 
                    COUNT(*) 
                FROM 
                    shows
                WHERE
                    title ILIKE CONCAT('%', ?, '%') AND show_date BETWEEN ? AND ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, showTitle, startDate, endDate);
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
    public List<Show> getShowsByArtistId(int artistId) {
        String sql = """
                SELECT *
                FROM
                    shows JOIN setlists ON shows.show_id = setlists.show_id
                    JOIN artists ON setlists.artist_id = artists.artist_id
                WHERE
                    artists.artist_id = ?   
                """;
        List<Show> results = jdbcTemplate.query(sql, this::mapRowToShow, artistId);
        return results;
    }

    @Override
    public int addShow(Show show) {
        String sql = "INSERT INTO shows (title, venue, show_date, start_time, duration) VALUES (?, ?, ?, ?, ?) RETURNING show_id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "show_id" });
            ps.setString(1, show.getTitle());
            ps.setString(2, show.getVenue());
            ps.setDate(3, show.getShowDate());
            ps.setTime(4, show.getStartTime());
            ps.setInt(5, show.getDuration());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
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
