package com.example.WombatFm.Configuration;

import java.sql.Time;
import java.text.SimpleDateFormat;

import org.springframework.core.convert.converter.Converter;

public class StringToTimeConverter implements Converter<String, Time> {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @Override
    public Time convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }

        try {
            java.util.Date parsedDate = TIME_FORMAT.parse(source);
            return new Time(parsedDate.getTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format. Please use 'HH:mm'.");
        }
    }
}
