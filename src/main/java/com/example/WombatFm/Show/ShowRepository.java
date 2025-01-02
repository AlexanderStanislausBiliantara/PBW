package com.example.WombatFm.Show;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.example.WombatFm.Artist.Artist;

public interface ShowRepository {
    List<Show> getAllShows();

    Optional<Show> getShowById(int showId);

    List<Show> getShowsByTitle(String showTitle);

    List<Show> getFilteredShows(String showTitle, Date startDate, Date endDate, int limit, int offset);

    List<Artist> getShowArtists(int showId);

    int countShows(String showTitle, Date startDate, Date endDate);

    int addShow(Show show);
}
