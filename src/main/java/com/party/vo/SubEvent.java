package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
public class SubEvent {
    private String eventName;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String speakerName;
    private String organizerName;
    private String organizationName;
    private String eventUrl;
    private String orgUrl;
    private String eventDescription;

}
