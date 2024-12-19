package com.example.WombatFm.Setlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetlistService {
    
    @Autowired
    private SetlistRepository setlistRepository;

    public List<Setlist> getAllSetlists() {
        return setlistRepository.findAll();
    }
}
