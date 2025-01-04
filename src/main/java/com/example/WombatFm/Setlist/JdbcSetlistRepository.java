package com.example.WombatFm.Setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Song.Song;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Song> getNewestSetlist(int showId) {
        String setlistSql = "SELECT sv.version_id AS version_id " +
                "FROM setlists s " +
                "JOIN setlist_version sv ON s.setlist_id = sv.setlist_id " +
                "WHERE s.show_id = ? " +
                "ORDER BY sv.version_id DESC " +
                "LIMIT 1";

        List<Integer> versionIdResult = jdbcTemplate.query(setlistSql, this::mapRowToVersionId, showId);

        if (versionIdResult.isEmpty()) {
            return new ArrayList<>();
        }

        int versionId = versionIdResult.get(0);

        String songsSql = "SELECT so.song_id AS song_id, so.title AS title, si.song_order AS song_order, so.artist_id AS artist_id  "
                +
                "FROM setlist_items si " +
                "JOIN songs so ON si.song_id = so.song_id " +
                "WHERE si.version_id = ? " +
                "ORDER BY si.song_order ASC";

        List<Song> songs = jdbcTemplate.query(songsSql, this::mapRowToSong, versionId);

        return songs;
    }

    @Override
    public Optional<Setlist> getSetlistByShowIdAndArtistId(int showId, int artistId) {
        String setlistSql = "SELECT s.setlist_id AS setlist_id, s.show_id AS show_id, s.artist_id AS artist_id, " +
                "sh.title AS title, sh.venue AS venue, " +
                "sh.show_date AS show_date, sh.start_time AS start_time, sh.duration AS duration, " +
                "a.artist_id AS artist_id, a.name AS name, a.artist_photo_url AS artist_photo_url " +
                "FROM setlists s " +
                "JOIN shows sh ON s.show_id = sh.show_id " +
                "JOIN artists a ON s.artist_id = a.artist_id " +
                "WHERE s.show_id = ? AND s.artist_id = ?";

        List<Setlist> setlists = jdbcTemplate.query(setlistSql, this::mapRowToSetlist, showId, artistId);

        return setlists.isEmpty() ? Optional.empty() : Optional.of(setlists.get(0));
    }

    @Override
    public List<Setlist> getTopTenSetlists() {
        String sql = """
                SELECT setlists.setlist_id, artists.name, artists.artist_id, artists.artist_photo_url, 
                shows.title, shows.venue, shows.show_id, shows.show_date, 
                shows.start_time, shows.duration
                FROM setlists JOIN shows ON setlists.show_id = shows.show_id
                JOIN artists ON setlists.artist_id = artists.artist_id
                ORDER BY setlist_id DESC FETCH FIRST 10 ROWS ONLY
                """;
        return jdbcTemplate.query(sql, this::mapRowToSetlist);
    }

    @Override
    public void createSetlist(int showId, int artistId) {
        String sql = "INSERT INTO setlists (show_id, artist_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, showId, artistId);
    }

    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        Show show = new Show(
                resultSet.getInt("show_id"),
                resultSet.getString("title"),
                resultSet.getString("venue"),
                resultSet.getDate("show_date"),
                resultSet.getTime("start_time"),
                resultSet.getInt("duration"));

        Artist artist = new Artist(
                resultSet.getInt("artist_id"),
                resultSet.getString("name"),
                resultSet.getString("artist_photo_url"));

        return new Setlist(resultSet.getInt("setlist_id"), show, artist, null);
    }

    private Song mapRowToSong(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song(
                resultSet.getInt("song_id"),
                resultSet.getString("title"),
                resultSet.getInt("artist_id"));
        song.setSongOrder(resultSet.getInt("song_order"));
        return song;
    }

    private int mapRowToVersionId(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("version_id");
    }    
}
