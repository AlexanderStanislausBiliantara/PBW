package com.example.WombatFm.Artist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArtistRepository implements ArtistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Artist> getAllArtists() {
        String sql = "SELECT * FROM artists";
        List<Artist> results = jdbcTemplate.query(sql, this::mapRowToArtist);
        return results;
    }

    @Override
    public Optional<Artist> getArtistById(int artistId) {
        String sql = "SELECT * FROM artists where artist_id = ?";
        List<Artist> results = jdbcTemplate.query(sql, this::mapRowToArtist, artistId);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public int addArtist(Artist artist) {
        String sql = "INSERT INTO artists (name, artist_photo_url) VALUES (?, ?) RETURNING artist_id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "artist_id" });
            ps.setString(1, artist.getName());
            ps.setString(2, artist.getPhotoUrl());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    private Artist mapRowToArtist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artist(
                resultSet.getInt("artist_id"),
                resultSet.getString("name"),
                resultSet.getString("artist_photo_url"));
    }
}
