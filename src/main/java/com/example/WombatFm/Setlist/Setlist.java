package com.example.WombatFm.Setlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Setlist {
    private int id;
    private String showTitle;
    private String versionNumber;
    private String artistName;
    private String concertDate;
    private String stadium;
    private String city;
    private List<String> songs;
}
