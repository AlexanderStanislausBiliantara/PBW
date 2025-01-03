package com.example.WombatFm.Song;

import java.util.List;
import java.util.Optional;

public interface SongRepository {
    List<Song> getAllSongs();

    Optional<Song> getSongById(int songId);

    int addSong(Song newSong);
}
