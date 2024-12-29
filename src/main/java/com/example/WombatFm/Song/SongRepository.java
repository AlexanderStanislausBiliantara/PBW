package com.example.WombatFm.Song;

import java.util.Optional;

public interface SongRepository {
    Optional<Song> getSongById(int songId);
}
