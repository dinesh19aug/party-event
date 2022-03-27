package com.party.service.subEvent.impl;

import com.party.service.IBaseService;
import com.party.service.subEvent.ISubEventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("getAllSubEvents")
public class SubEventServiceReadAll implements ISubEventService<SubEventStatus>, IBaseService {
    SessionFactory sessionFactory;

    @Inject
    public SubEventServiceReadAll(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public SubEventStatus process(Object... args) {
        return getAllSubEvents((long) args[0]);
    }

    public SubEventStatus getAllSubEvents(long eventId) {
        new ArrayList<>();
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();

        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);
        optionalEvent.ifPresentOrElse((event)->{
            //event.getSubEvent().forEach(subEvent -> subEventList.add(subEvent));
            List<SubEvent> subEventList =getOptionalSubEventBySubEventId(eventId,session);
            subEventStatus.setSubEvents(subEventList);
            subEventStatus.setStatus("Found: "+ (long) subEventList.size() + " sub events");

        },()->{
            subEventStatus.setStatus("0 events founds");
            subEventStatus.setError(EventError.builder().errorDesc("Event: " + eventId + " not found").build());
        });
        return subEventStatus;
    }


}
