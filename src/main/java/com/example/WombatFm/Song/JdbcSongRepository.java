package com.example.WombatFm.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSongRepository implements SongRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Song> getSongById(int songId) {
        String sql = "SELECT s.song_id AS song_id, s.title AS title "
                + "FROM songs s "
                + "JOIN artists a ON s.artist_id = a.artist_id "
                + "WHERE song_id = ? ";
        List<Song> results = jdbcTemplate.query(sql, this::mapRowToSong, songId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    private Song mapRowToSong(ResultSet resultSet, int rowNum) throws SQLException {
        return new Song(resultSet.getInt("song_id"), resultSet.getString("title"));
    }
}
