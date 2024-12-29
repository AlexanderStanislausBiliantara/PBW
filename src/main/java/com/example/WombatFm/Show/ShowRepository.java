package com.example.WombatFm.Show;

import java.util.List;
import java.util.Optional;

import com.example.WombatFm.Artist.Artist;

public interface ShowRepository {
    List<Show> getAllShows();

    Optional<Show> getShowById(int showId);

    List<Show> getShowsByTitle(String showTitle);

    List<Artist> getShowArtists(int showId);

    int addShow(Show show);
}
