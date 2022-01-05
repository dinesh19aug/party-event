
package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dines
 */
@Data
@NodeEntity
public class Person {
    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String MobileNumber;

    @Property
    private String emailAddress;

    @Relationship(type = "is_invited", direction = Relationship.INCOMING)
    Set<Event> event = new HashSet<>();
}
