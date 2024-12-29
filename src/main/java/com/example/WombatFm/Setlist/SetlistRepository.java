package com.example.WombatFm.Setlist;

import java.util.List;

import com.example.WombatFm.Song.Song;

public interface SetlistRepository {
    List<Song> getNewestSetlist(int setlistId);

    void createSetlist(int showId, int artistId);
}
