package com.example.WombatFm.Setlist;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SetlistRepository  {

    List<Setlist> findAll();
    
}
