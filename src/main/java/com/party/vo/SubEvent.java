package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NodeEntity
public class SubEvent {
    @Id
    @GeneratedValue
    Long id;
    @Property(name = "event_name")
    private String eventName;
    @Property(name = "event_start_date")
    private LocalDate eventStartDate;
    @Property(name = "event_end_date")
    private LocalDate eventEndDate;
    @Property(name = "start_time")
    private LocalTime startTime;
    @Property(name = "end_time")
    private LocalTime endTime;
    @Property(name = "speaker_name")
    private String speakerName;
    @Property(name = "organizer_name")
    private String organizerName;
    @Property(name = "organization_name")
    private String organizationName;
    @Property(name = "event_url")
    private String eventUrl;
    @Property(name = "org_url")
    private String orgUrl;
    @Property(name = "event_description")
    private String eventDescription;

}
