package com.example.WombatFm.Show;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.example.WombatFm.Artist.Artist;

public interface ShowRepository {
    List<Show> getAllShows();

    Optional<Show> getShowById(int showId);

    List<Show> getShowsByTitle(String showTitle, int limit, int offset);

    List<Show> getFilteredShows(String showTitle, Date startDate, Date endDate, int limit, int offset);

    List<Artist> getShowArtists(int showId);

    int countFilteredShows(String showTitle, Date startDate, Date endDate);

    int countShows(String showTitle);

    int addShow(Show show);

    List<Show> getShowsByArtistId(int artistId);
}
