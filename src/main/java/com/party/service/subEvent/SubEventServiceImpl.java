package com.party.service.subEvent;

import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped

public class SubEventServiceImpl extends SubEventService {
    SessionFactory sessionFactory;

    //TODO Break this into single services. Every change in this class for new services means retesting all methods. Breaks SOLID principle
    public SubEventServiceImpl() {
    }

    @Inject
    public SubEventServiceImpl(SessionFactory s) {
        this.sessionFactory = s;
    }





    /**
     * Delete subevent by subeventId for a given eventId
     *
     * @param eventId    Event Id
     * @param subEventId Subevent Id
     * @return SubEventStatus
     */
    @Override
    public SubEventStatus deleteSubEventById(long eventId, long subEventId) {
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);

        optionalEvent.ifPresentOrElse((event) -> {
            Optional<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId, session).stream().filter(se -> se.getId() == subEventId).findFirst();
            resultSE.ifPresentOrElse(subEventTobeDeleted -> {
                //Run delete
                runInTransaction(() -> {
                   /* session.query(SubEvent.class,"MATCH(e:Event)-[r]->(s:SubEvent)\n" +
                            "WHERE ID(e)=$eventId AND ID(s)=$subeventId \n" +
                            "DETACH DELETE s", Map.of("eventId", eventId, "subeventId", subEventId));*/
                    session.delete(subEventTobeDeleted);
                    subEventStatus.setStatus("SubEvent: ".concat(String.valueOf(subEventId)).concat(" is deleted for eventId: ").concat(String.valueOf(eventId)));
                }, session);
            }, () -> {// Subevent not found
                subEventStatus.setStatus("Failed to delete SubEvent: " + subEventId);
                EventError error = EventError.builder().errorDesc("SubEvent not found").build();
                subEventStatus.setError(error);
            });

            //Event not found
        }, () -> subEventStatus.setError(EventError.builder().errorDesc("Event with id " + eventId + " subEvent Id: " + subEventId + " is not found").build()));
        return subEventStatus;

    }

    @Override
    public SubEventStatus deleteAllSubeventByEventId(long eventId) {
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);
        optionalEvent.ifPresentOrElse((event -> {
                    List<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId, session);
                    for (SubEvent s : resultSE) {
                        runInTransaction(() -> session.delete(s), session);
                    }
                    subEventStatus.setStatus("All Subevents deleted for Event: ".concat(String.valueOf(eventId)));
                })
                , () -> {//Event not found
                    subEventStatus.setError(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build());
                });
        return subEventStatus;
    }


    /*private Optional<Event> getOptionalEventById(long eventId, Session session) {
        return Optional.ofNullable(session.load(Event.class, eventId));
    }*/

    private List<SubEvent> getOptionalSubEventBySubEventId(long eventId, Session session) {
        return (List<SubEvent>) session.query(SubEvent.class, "MATCH (e:Event )-[:HAS_SUBEVENT]->(s:SUB_EVENT) WHERE ID(e)=" + eventId + " RETURN s", Collections.emptyMap());
    }

}
