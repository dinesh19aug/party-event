
package com.party.vo;

import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author dinesh
 */
@Data
@NodeEntity("Event")
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



  @Relationship(type = "HAS_SUBEVENT", direction = Relationship.OUTGOING)
  Set<SubEvent> subEvent = new HashSet<>();

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




}
