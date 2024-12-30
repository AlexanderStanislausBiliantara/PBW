package com.example.WombatFm.Review;

import com.example.WombatFm.Song.Song;

import java.sql.Date;
import java.util.List;
import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Review {

    @Nonnull
    private int reviewId;

    @Nonnull
    private int userId;

    @Nonnull
    private int setlistId;

    @Nonnull
    private String comment;

    private List<Song> songs;

    @Nonnull
    private Date createdAt;

    @Override
    public String toString() {
        String result = "reviewId=" + reviewId +
                ", userId=" + userId +
                ", setlistId=" + setlistId +
                ", comment='" + comment +
                ", createdAt='" + createdAt.toString();

        if (songs != null) {
            result += ", songs size =" + songs.size();
        }

        return result;
    }
}
