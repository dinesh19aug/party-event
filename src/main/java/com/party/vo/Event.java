
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



  @Relationship(type = "HAS_SUBEVENT")
  Set<SubEvent> subEvent = new HashSet<>();

  @Relationship(type = "IS_INVITED")
  Set<Person> person = new HashSet<>();





}
