package com.example.WombatFm.Setlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Setlist> findAll() {
        String sql = "SELECT * FROM setlists";
        List<Setlist> results = jdbcTemplate.query(sql, this::mapRowToSetlist);
        return results;
    }

    @Override
    public Optional<Setlist> findById(int idSetlist) {
        String sql = "SELECT * FROM setlists WHERE id = ?";
        List<Setlist> results = jdbcTemplate.query(sql, this::mapRowToSetlist, idSetlist);
        return results.size() == 0 ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Setlist save(Setlist setlist) {
        String sql = "INSERT INTO setlists (showTitle, versionNumber, artistName, concertDate, stadium, city, songs) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, setlist.getShowTitle(), setlist.getVersionNumber(), setlist.getArtistName(),
                setlist.getConcertDate(), setlist.getStadium(), setlist.getCity(), setlist.getSongs());
        return setlist;
    }

    @Override
    public void delete(Setlist setlist) {
        String sql = "DELETE FROM setlists WHERE id = ?";
        jdbcTemplate.update(sql, setlist.getId());
    }

    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
                resultSet.getInt("id"),
                resultSet.getString("show_title"),
                resultSet.getString("version_number"),
                resultSet.getString("artist_name"),
                resultSet.getString("concert_date"),
                resultSet.getString("stadium"),
                resultSet.getString("city"),
                null);
    }
}
