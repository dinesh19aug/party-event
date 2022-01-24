package com.party.vo.relation;

import com.party.vo.Event;
import com.party.vo.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity("IS_INVITED")
@Data
@AllArgsConstructor
@ToString @EqualsAndHashCode
public class RInvite {
    private RInvite(){}

    @StartNode
    private Event event;

    @EndNode
    private Person person;

    @Property
    private String role;

}
