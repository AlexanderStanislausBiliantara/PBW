package com.example.WombatFm.Setlist;

import java.util.List;

import com.example.WombatFm.Artist.Artist;
import com.example.WombatFm.Show.Show;
import com.example.WombatFm.Song.Song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setlist {

    private int setlistId;

    private Show show;

    private Artist artist;

    private List<Song> songs;
}
