package com.example.WombatFm.Setlist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetlistService {

    @Autowired
    private SetlistRepository setlistRepository;

    public List<Setlist> getAllSetlists() {
        return setlistRepository.findAll();
    }

    public Setlist addSetlist(Setlist setlist) {
        return setlistRepository.save(setlist);
    }

    public Setlist updateSetlist(int id, Setlist setlistDetails) {
        Optional<Setlist> setlist = setlistRepository.findById(id);
        if (setlist.isPresent()) {
            setlist.get().setShowTitle(setlistDetails.getShowTitle());
            setlist.get().setVersionNumber(setlistDetails.getVersionNumber());
            setlist.get().setConcertDate(setlistDetails.getConcertDate());
            setlist.get().setStadium(setlistDetails.getStadium());
            setlist.get().setCity(setlistDetails.getCity());
            setlist.get().setSongs(setlistDetails.getSongs());
            return setlistRepository.save(setlist.get());
        }
        return null;
    }

    public void deleteSetlist(int id) {
        Optional<Setlist> setlist = setlistRepository.findById(id);
        if (setlist.isPresent()) {
            setlistRepository.delete(setlist.get());
        }
    }
}
