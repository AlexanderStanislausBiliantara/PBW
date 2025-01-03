package com.example.WombatFm.Review;

import com.example.WombatFm.Song.Song;

import java.sql.Date;
import java.util.List;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Review {

    @Nonnull
    private int reviewId;

    @Nonnull
    @Min(value = 1, message = "userId is required")
    private int userId;

    @Nonnull
    @Min(value = 1, message = "setlistId is required")
    private int setlistId;

    @Nonnull
    @NotBlank(message = "comment is required")
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
