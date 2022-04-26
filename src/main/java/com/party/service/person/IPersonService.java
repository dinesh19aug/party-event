package com.party.service.person;

import com.party.vo.Person;
import org.neo4j.ogm.session.Session;

import java.util.*;

public interface IPersonService<T> {
    T process(Object... args);

    default List<Person> getAllGuestByEventId(Session session, long eventId) {
        final String query = "MATCH (e:Event )-[:IS_INVITED]->(p:Person) " +
                "WHERE ID(e)=$eventId  " +
                "RETURN p";
        Map<String, Object> params = new HashMap<>();
        params.put("eventId", eventId);
        return (List<Person>) session.query(Person.class, query, params);
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

    default void deleteGuestById(long eventId, long personId, Session session) {
        final String query = "MATCH (e:Event)-[:IS_INVITED]->(p:Person) " +
                "WHERE ID(e)=$eventId and ID(p)=$personId " +
                "DETACH DELETE p";
        Map<String, Object> params = new HashMap<>();
        params.put("eventId", eventId);
        params.put("personId", personId);
        session.query(Person.class, query,params);
    }

    default Person getPersonMatch(long eventId, Person person, Session session){
        String query = "MATCH (e:Event)-[:IS_INVITED]->(p:Person) " +
                "WHERE ID(e) = $eventId AND p.mobileNumber = $mobileNumber AND p.emailAddress = $email " +
                "RETURN p";
        Map<String, Object> params = new HashMap<>();
        params.put("eventId", eventId);
        params.put("mobileNumber", person.getMobileNumber());
        params.put("email", person.getEmailAddress());
        return session.queryForObject(Person.class, query, params);
    }


}
