package com.party.vo.converter;


import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

    @Override public String toGraphProperty(LocalTime value) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm]a");
        return value.format(parser).toString();
    }

    @Override public LocalTime toEntityAttribute(String value) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm]a");
        return LocalTime.parse("10AM", parser);

    }
}