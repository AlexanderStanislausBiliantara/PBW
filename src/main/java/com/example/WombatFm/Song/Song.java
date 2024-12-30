package com.example.WombatFm.Song;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Song {
    @Nonnull
    private int songId;

    @Nonnull
    private String title;

    @Nonnull
    private int artistId;

    private int songOrder;
}
