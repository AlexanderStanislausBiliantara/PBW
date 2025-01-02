package com.example.WombatFm.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository {
    Optional<Artist> getArtistById(int artistId);

    List<Artist> getAllArtists();

    int addArtist(Artist artist);
}
