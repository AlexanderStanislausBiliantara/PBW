package com.example.WombatFm.Configuration;

import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.example.WombatFm.Song.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringToSongListConverter implements Converter<String, List<Song>> {

    @Override
    public List<Song> convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        System.out.println("source: " + source);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Song> songs = objectMapper.readValue(source, new TypeReference<List<Song>>() {
            });
            // for (Song song : Songs) {
            // System.out.println("Song ID: " + song.getSongId() + ", Title: " +
            // song.getTitle() + ", SongOrder: "
            // + song.getSongOrder());
            // }
            return songs;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing songs: " + e.getMessage());
        }
    }
}
