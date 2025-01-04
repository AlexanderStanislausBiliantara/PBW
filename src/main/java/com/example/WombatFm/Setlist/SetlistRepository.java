package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import com.example.WombatFm.Song.Song;

public interface SetlistRepository {
    List<Song> getNewestSetlist(int showId);

    Optional<Setlist> getSetlistByShowIdAndArtistId(int showId, int artistId);

    void createSetlist(int showId, int artistId);

    List<Setlist> getTopTenSetlists();
}
