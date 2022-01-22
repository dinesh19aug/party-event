
package com.party.vo;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.util.List;
import java.util.Set;


/**
 *
 * @author dinesh
 */

@NodeEntity
public class Event {
  @Id
  @GeneratedValue
  @Index
  private Long id;

  @Property
  private String eventName;

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
  Set<Person> guests;

  @Relationship(type = "has_subevent")
  List<SubEvent> subEvent;

  public List<SubEvent> getSubEvent() {
    return subEvent;
  }

  public void setSubEvent(List<SubEvent> subEvent) {
    this.subEvent = subEvent;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
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



}
