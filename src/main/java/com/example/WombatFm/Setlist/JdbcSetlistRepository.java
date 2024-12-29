package com.example.WombatFm.Setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.WombatFm.Song.Song;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Song> getNewestSetlist(int setlistId) {
        String setlistSql = "SELECT sv.version_id AS version_id " +
                "FROM setlists s " +
                "JOIN setlist_version sv ON s.setlist_id = sv.setlist_id " +
                "WHERE s.show_id = ? " +
                "ORDER BY sv.version_id DESC " +
                "LIMIT 1";

        List<Integer> versionIdResult = jdbcTemplate.query(setlistSql, this::mapRowToVersionId, setlistId);

        if (versionIdResult.isEmpty()) {
            return new ArrayList<>();
        }

        int versionId = versionIdResult.get(0);

        String songsSql = "SELECT so.song_id AS song_id, so.title AS title, si.song_order AS song_order " +
                "FROM setlist_items si " +
                "JOIN songs so ON si.song_id = so.song_id " +
                "WHERE si.version_id = ? " +
                "ORDER BY si.song_order ASC";

        List<Song> songs = jdbcTemplate.query(songsSql, this::mapRowToSong, versionId);

        return songs;
    }

    @Override
    public void createSetlist(int showId, int artistId) {
        String sql = "INSERT INTO setlists (show_id, artist_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, showId, artistId);
    }

    private Song mapRowToSong(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song(resultSet.getInt("song_id"), resultSet.getString("title"));
        song.setSongOrder(resultSet.getInt("song_order"));
        return song;
    }

    private int mapRowToVersionId(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("version_id");
    }
}
