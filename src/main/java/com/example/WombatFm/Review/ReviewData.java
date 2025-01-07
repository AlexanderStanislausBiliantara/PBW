package com.example.WombatFm.Review;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewData {
    private Date day;
    private int reviewCount;
}
