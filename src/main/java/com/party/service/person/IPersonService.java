package com.party.service.person;

import com.party.vo.Person;
import org.neo4j.ogm.session.Session;

import java.util.Collections;
import java.util.List;

public interface IPersonService<T> {
    T process(Object... args);

    default List<Person> getAllGuestByEventId(Session session, long eventId) {
        return (List<Person>) session.query(Person.class, "MATCH (e:Event )-[:IS_INVITED]->(p:Person) WHERE ID(e)=" + eventId + " RETURN p", Collections.emptyMap());
    }
}
