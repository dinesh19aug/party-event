package com.party.vo;

import com.party.vo.converter.LocalTimeConverter;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.annotation.typeconversion.DateString;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NodeEntity
public class SubEvent {
    @Id
    @GeneratedValue
    Long id;
    @Property(name = "event_name")
    private String eventName;

    @Property(name = "event_start_date")
    @DateString("yyyy-MM-dd")
    private Date eventStartDate;

    @Property(name = "event_end_date")
    @DateString("yyyy-MM-dd")
    private Date eventEndDate;

  @Property(name = "start_time")
    @Convert(LocalTimeConverter.class)
      private LocalTime startTime;

  @Convert(LocalTimeConverter.class)
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

    @Relationship(type = "has_subevent", direction = Relationship.INCOMING)
    Set<Event> event = new HashSet<>();


}
