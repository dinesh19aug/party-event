
package com.party.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.EnumString;

/**
 *
 * @author dinesh
 */
@Data
@AllArgsConstructor
@NodeEntity
public class Event {
  @Id
  private String name;



  @Property()
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

}
