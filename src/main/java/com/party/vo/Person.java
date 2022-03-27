
package com.party.vo;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author dines
 */
@Data
@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    Long id;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String mobileNumber;

    @Property
    private String emailAddress;

    @Relationship(type = "IS_INVITED", direction = Relationship.INCOMING)
    Set<Event> event = new HashSet<>();
}
