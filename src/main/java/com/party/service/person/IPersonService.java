package com.party.service.person;

import com.party.vo.Person;
import org.neo4j.ogm.session.Session;

import java.util.*;

public interface IPersonService<T> {
    T process(Object... args);

    default List<Person> getAllGuestByEventId(Session session, long eventId) {
        return (List<Person>) session.query(Person.class, "MATCH (e:Event )-[:IS_INVITED]->(p:Person) WHERE ID(e)=" + eventId + " RETURN p", Collections.emptyMap());
    }

    default Person getPersonById(long eventId, long personId, Session session) {
        final String query = "MATCH (e:Event )-[:IS_INVITED]->(p:Person) " +
                "WHERE ID(e)=$eventId AND  ID(p)=$personId " +
                " RETURN p";
        Map<String, Object> params = new HashMap<>();
        params.put("eventId", eventId);
        params.put("personId", personId);
         return  session.queryForObject(Person.class, query,params);
    }
}
