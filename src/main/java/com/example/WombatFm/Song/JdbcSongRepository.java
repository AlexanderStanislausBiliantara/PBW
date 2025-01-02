package com.example.WombatFm.Song;

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
public class JdbcSongRepository implements SongRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Song> getSongById(int songId) {
        String sql = "SELECT s.song_id AS song_id, s.title AS title, a.artist_id AS artist_id "
                + "FROM songs s "
                + "JOIN artists a ON s.artist_id = a.artist_id "
                + "WHERE song_id = ? ";
        List<Song> results = jdbcTemplate.query(sql, this::mapRowToSong, songId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public int addSong(Song newSong) {
        String sql = "INSERT INTO songs (title, artist_id) VALUES (?, ?) RETURNING song_id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "song_id" });
            ps.setString(1, newSong.getTitle());
            ps.setInt(2, newSong.getArtistId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    private Song mapRowToSong(ResultSet resultSet, int rowNum) throws SQLException {
        return new Song(
                resultSet.getInt("song_id"),
                resultSet.getString("title"),
                resultSet.getInt("artist_id"));
    }
}
