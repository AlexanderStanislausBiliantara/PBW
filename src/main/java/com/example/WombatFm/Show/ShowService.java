package com.example.WombatFm.Show;

import java.sql.Date;
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

    public List<Show> getShowWithPagination(String showTitle, Date startDate, Date endDate, int page, int size) {
        int offset = (page - 1) * size;
        return showRepository.getFilteredShows(showTitle, startDate, endDate, size, offset);
    }

    public int getTotalShowCount(String showTitle, Date startDate, Date endDate) {
        return showRepository.countShows(showTitle, startDate, endDate);
    }

    public int getPageCount(String showTitle, Date startDate, Date endDate, int size) {
        int rowCount = getTotalShowCount(showTitle, startDate, endDate);
        return (int) Math.ceil((double) rowCount / size);
    }
}
