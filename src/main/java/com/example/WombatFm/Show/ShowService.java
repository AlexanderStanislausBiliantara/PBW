package com.example.WombatFm.Show;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WombatFm.Artist.Artist;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> getAllShows() {
        return showRepository.getAllShows();
    }

    public Optional<Show> getShowById(int showId) {
        return showRepository.getShowById(showId);
    }

    public List<Show> getShowsByTitle(String title) {
        return showRepository.getShowsByTitle(title);
    }

    public List<Artist> getShowArtists(int showId) {
        return showRepository.getShowArtists(showId);
    }

    public int addShow(Show show) throws IllegalArgumentException {

        if (show.getTitle() == null || show.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Show title cannot be empty");
        }
        if (show.getVenue() == null || show.getVenue().isEmpty()) {
            throw new IllegalArgumentException("Show venue cannot be empty");
        }
        if (show.getShowDate() == null) {
            throw new IllegalArgumentException("Show date cannot be empty");
        }
        if (show.getStartTime() == null) {
            throw new IllegalArgumentException("Show start time cannot be empty");
        }
        if (show.getDuration() <= 0) {
            throw new IllegalArgumentException("Show duration must be greater than zero");
        }

        return showRepository.addShow(show);
    }

}
