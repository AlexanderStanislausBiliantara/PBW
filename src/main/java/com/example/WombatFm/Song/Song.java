package com.example.WombatFm.Song;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Song {
    @Nonnull
    private int songId;

    @Nonnull
    @NotBlank(message = "Title is required")
    private String title;

    @Nonnull
    @Min(value = 1, message = "Choose an artist")
    private int artistId;

    private int songOrder;
}
