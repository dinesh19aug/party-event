package com.party.service.impl.subEvent;


import com.party.vo.Event;
import com.party.vo.SubEvent;
import org.neo4j.ogm.session.Session;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface ISubEventService<T> {

    T process(Object... args);

    default Optional<Event> getOptionalEventById(long eventId, Session session) {
        return Optional.ofNullable(session.load(Event.class, eventId));
    }

    default List<SubEvent> getOptionalSubEventBySubEventId(long eventId, Session session) {
        return (List<SubEvent>) session.query(SubEvent.class, "MATCH (e:Event )-[:HAS_SUBEVENT]->(s:SubEvent) WHERE ID(e)=" + eventId + " RETURN s", Collections.emptyMap());
    }

}
