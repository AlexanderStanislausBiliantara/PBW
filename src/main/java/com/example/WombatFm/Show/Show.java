package com.example.WombatFm.Show;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    @Min(value = 1)
    private int showId;

    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    private String venue;

    @NotNull(message = "Show date is required")
    private Date showDate;

    @NotNull(message = "Show start time is required")
    private Time startTime;

    @NotNull
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int duration;

    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", title='" + title + '\'' +
                ", venue='" + venue + '\'' +
                ", showDate=" + showDate +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    public String getDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
        return formatter.format(showDate);
    }
}