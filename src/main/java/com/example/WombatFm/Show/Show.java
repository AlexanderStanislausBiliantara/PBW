package com.example.WombatFm.Show;

import java.sql.Date;
import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Show {
    private int showId;
    private String title;
    private String venue;
    private Date showDate;
    private Time startTime;
    private int duration;
}