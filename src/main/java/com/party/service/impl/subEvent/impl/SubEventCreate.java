package com.party.service.impl.subEvent.impl;

import com.party.service.IBaseService;
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
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Named("subEventCreate")
public class SubEventCreate implements ISubEventService, IBaseService {
    SessionFactory sessionFactory;

    @Inject
    public SubEventCreate(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public Object process(Object... args) {
        return create((SubEvent) args[0], (long) args[1]);
    }


    private SubEventStatus create(SubEvent subEvent, Long eventId) {
        Session session = sessionFactory.openSession();
        SubEventStatus subEventStatus =new  SubEventStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse((event -> {
            Optional<Set<SubEvent>> optionalSubEventList = Optional.ofNullable(event.getSubEvent());
            optionalSubEventList.ifPresentOrElse((subEventList)->{
                        subEventList.add(subEvent);
                        runInTransaction(() -> {
                            event.setSubEvent(subEventList);
                            session.save(event);
                            subEventStatus.setStatus("SubEvent added to event with id: " + eventId);
                        }, session);
                    }
                    ,()-> runInTransaction(() -> {
                        event.setSubEvent(Collections.singleton(subEvent));
                        session.save(event);
                        subEventStatus.setStatus("SubEvent added to event with id: " + eventId);
                    }, session));

        }), ()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before adding subEvents").build();
            subEventStatus.setError(error);
        });
        return subEventStatus;
    }
}
