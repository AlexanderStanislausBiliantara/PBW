package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

public interface SetlistRepository {
    List<Setlist> findAll();

    Optional<Setlist> findById(int id);

    Setlist save(Setlist setlist);

    void delete(Setlist setlist);
}
