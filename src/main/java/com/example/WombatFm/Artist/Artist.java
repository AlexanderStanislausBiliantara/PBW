package com.example.WombatFm.Artist;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artist {
    private int artistId;
    private String name;
    private String photoUrl;
}
