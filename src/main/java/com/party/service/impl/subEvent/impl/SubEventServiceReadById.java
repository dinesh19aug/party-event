package com.party.service.impl.subEvent.impl;

import com.party.service.impl.subEvent.ISubEventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("getSubEventById")
public class SubEventServiceReadById implements ISubEventService {

    SessionFactory sessionFactory;

    @Inject
    public SubEventServiceReadById(SessionFactory s){
        this.sessionFactory = s;
    }


    @Override
    public SubEventStatus process(Object... args) {
        return  getSubEventById((Long) args[0] ,(Long) args[1]);
    }


    public SubEventStatus getSubEventById(long eventId, long subEventId) {
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);
        optionalEvent.ifPresentOrElse((event -> {
                    List<SubEvent> resultSE = getOptionalSubEventByEventIdAndSubEventId(eventId, subEventId, session);
                    if(resultSE.size()>0){
                        subEventStatus.setSubEvents(resultSE);
                        subEventStatus.setStatus(String.valueOf(resultSE.size()).concat(" Subevent found for subEventId: ")
                                .concat(String.valueOf(subEventId)).concat(" and EventId: ")
                                .concat(String.valueOf(eventId)));
                    }else{
                        subEventStatus.setStatus(" No Subevent found for subEventId: "
                                .concat(String.valueOf(subEventId)).concat(" and EventId: ")
                                .concat(String.valueOf(eventId)));
                    }

                })
                , () ->{//Event not found
                    subEventStatus.setError(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build());
                });
        return subEventStatus;
    }

    private List<SubEvent> getOptionalSubEventByEventIdAndSubEventId(long eventId, long subEventId, Session session) {
        return (List<SubEvent>) session.query(SubEvent.class,"MATCH (e:Event )-[:HAS_SUBEVENT]->(s:SubEvent) WHERE ID(e)=" + eventId
                + " AND ID(s)=" + subEventId
                + " RETURN s", Collections.emptyMap());
    }



}
