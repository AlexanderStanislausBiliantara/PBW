package com.example.WombatFm.Artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        return artistRepository.getAllArtists();
    }

    public Optional<Artist> getArtistById(int artistId) {
        return artistRepository.getArtistById(artistId);
    }

    public int addArtist(Artist artist) throws IllegalArgumentException {
        if (artist.getName() == null || artist.getName().isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty");
        }
        return artistRepository.addArtist(artist);
    }

}
