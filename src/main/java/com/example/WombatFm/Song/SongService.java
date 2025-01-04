package com.example.WombatFm.Song;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return songRepository.getAllSongs();
    }

    public Optional<Song> getSongById(int songId) {
        return songRepository.getSongById(songId);
    }

    public int addSong(Song newSong) {
        int songId = songRepository.addSong(newSong);
        return songId;
    }
}
