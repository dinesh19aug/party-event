
package com.party.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.EnumString;
import org.neo4j.ogm.id.UuidStrategy;


/**
 *
 * @author dinesh
 */

@NodeEntity
public class Event {
  @Id @GeneratedValue Long id;

  @Property @Id
  private String name;

  @Property
  @EnumString(Type.class)
  private Type eventType;
  @Property
  private  String orgName;
  @Property
  private String orgUrl;
  @Property
  private String eventUrl;

  @Relationship(type = "is_invited")
  Set<Person> guests= new HashSet<>();

  @Relationship(type = "has_subevent")
  Set<SubEvent> subEvents = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getEventType() {
    return eventType;
  }

  public void setEventType(Type eventType) {
    this.eventType = eventType;
  }

  public String getOrgName() {
    return orgName;
  }

  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  public String getOrgUrl() {
    return orgUrl;
  }

  public void setOrgUrl(String orgUrl) {
    this.orgUrl = orgUrl;
  }

  public String getEventUrl() {
    return eventUrl;
  }

  public void setEventUrl(String eventUrl) {
    this.eventUrl = eventUrl;
  }

  public Set<Person> getGuests() {
    return guests;
  }

  public void setGuests(Set<Person> guests) {
    this.guests = guests;
  }

  public Set<SubEvent> getSubEvents() {
    return subEvents;
  }

  public void setSubEvents(Set<SubEvent> subEvents) {
    this.subEvents = subEvents;
  }
}
