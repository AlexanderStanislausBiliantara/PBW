package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WombatFm.Song.Song;

@Service
public class SetlistService {

    @Autowired
    private SetlistRepository setlistRepository;

    public Optional<Setlist> getNewestSetlist(int showId, int artistId) {
        // Optional<Show> showDetails = showRepository.getShowById(showId);
        // Optional<Artist> artistDetails = artistRepository.getArtistById(artist_id);

        Optional<Setlist> setlistDetails = setlistRepository.getSetlistByShowIdAndArtistId(showId, artistId);
        // if (showDetails.isPresent() && artistDetails.isPresent()) {
        if (setlistDetails.isPresent()) {
            List<Song> songs = setlistRepository.getNewestSetlist(showId, artistId);
            // Setlist setlist = new Setlist(0, showDetails.get(), artistDetails.get(),
            // songs);
            Setlist setlist = setlistDetails.get();
            setlist.setSongs(songs);

            return Optional.of(setlist);
        }
        return Optional.empty();
    }

    public Optional<Setlist> getSetlistByShowIdAndArtistId(int showId, int artistId) {
        return setlistRepository.getSetlistByShowIdAndArtistId(showId, artistId);
    }

    public void createSetlist(int showId, int artistId) {
        setlistRepository.createSetlist(showId, artistId);
    }

    public List<Setlist> getTopSetlists(int limit) {
        return setlistRepository.getTopSetlists(limit);
    }
}
