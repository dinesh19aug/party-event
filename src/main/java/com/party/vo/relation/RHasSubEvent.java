package com.party.vo.relation;

import com.party.vo.Event;
import com.party.vo.Person;
import com.party.vo.SubEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity
@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RHasSubEvent {
    @StartNode
    private Event event;

    @EndNode
    private SubEvent person;
}
