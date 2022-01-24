package com.party.vo.relation;

import com.party.vo.Event;
import com.party.vo.SubEvent;
import lombok.*;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity("HAS_SUBEVENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RHasSubEvent {
    @StartNode
    Event event;
    @EndNode
    SubEvent subEvent;
}
