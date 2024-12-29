package com.example.WombatFm.Setlist;

import java.util.List;

import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Song.Song;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Setlist {
    private Show show;
    private List<Song> songs;
}
