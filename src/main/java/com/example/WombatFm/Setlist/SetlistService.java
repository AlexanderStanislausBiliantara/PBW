package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Show.ShowRepository;
import com.example.WombatFm.Song.Song;

@Service
public class SetlistService {

    @Autowired
    private SetlistRepository setlistRepository;

    @Autowired
    private ShowRepository showRepository;

    public Optional<Setlist> getNewestSetlist(int showId, int artist_id) {
        Optional<Show> showDetails = showRepository.getShowById(showId);
        if (showDetails.isPresent()) {
            List<Song> songs = setlistRepository.getNewestSetlist(showId);
            Setlist setlist = new Setlist(showDetails.get(), songs);

            return Optional.of(setlist);
        }
        return Optional.empty();
    }

    public Optional<Setlist> getShowArtists(int showId, int artist_id) {
        Optional<Show> showDetails = showRepository.getShowById(showId);
        if (showDetails.isPresent()) {
            List<Song> songs = setlistRepository.getNewestSetlist(showId);
            Setlist setlist = new Setlist(showDetails.get(), songs);

            return Optional.of(setlist);
        }
        return Optional.empty();
    }

    public void createSetlist(int showId, int artistId) {
        setlistRepository.createSetlist(showId, artistId);
    }

}
