package com.example.WombatFm.Configuration;

import java.sql.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Date.valueOf(source);
    }
}
