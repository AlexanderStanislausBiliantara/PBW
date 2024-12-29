package com.example.WombatFm.Gallery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.WombatFm.Artist.Artist;

@Repository
public class JdbcGalleryRepository implements GalleryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Gallery> getShowImages() {
        String sql = "SELECT * FROM gallery WHERE show_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToGallery);
    }

    @Override
    public int addShowImage(Gallery gallery) {
        String sql = "INSERT INTO gallery (show_id, image_url) VALUES (?, ?)";

        return jdbcTemplate.update(sql, gallery.getShowId(), gallery.getImageUrl());
    }

    @Override
    public int addShowImages(List<Gallery> galleries) {
        String sql = "INSERT INTO gallery (show_id, image_url) VALUES (?, ?)";

        int count = 0;

        for (Gallery gallery : galleries) {
            int rows = jdbcTemplate.update(sql, gallery.getShowId(), gallery.getImageUrl());
            count += rows;
        }
        return count;
    }

    private Gallery mapRowToGallery(ResultSet resultSet, int rowNum) throws SQLException {
        return new Gallery(
                resultSet.getInt("gallery_id"),
                resultSet.getInt("show_id"),
                resultSet.getString("image_url"));
    }
}
