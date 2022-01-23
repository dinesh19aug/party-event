package com.party.service.impl;

import com.party.service.EventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.status.EventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * @author dines
 */
@ApplicationScoped
public class EventServiceImpl extends EventService {
    @Inject
    SessionFactory sessionFactory;

    //TODO When the event does not exist create Event else throw error
    @Override
    public EventStatus create(Event event) {
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent= Optional.ofNullable( session.queryForObject(Event.class, "MATCH(p:Event) WHERE p.eventName ='" + event.getEventName()  +"' RETURN p", Collections.emptyMap()));
        //Optional<Event> optionalEvent = Optional.ofNullable(session.load(Event.class, event.getEventName()));
        EventStatus.EventStatusBuilder eventStatus = EventStatus.builder();
        optionalEvent.ifPresentOrElse((e) -> {
            eventStatus.status("Event is not created");
            eventStatus.error(EventError.builder().errorDesc("Event with id " + e.getEventName() + " already exists").build());
        }, () -> runInTransaction(() -> {
            session.save(event);
            eventStatus.status("Event is created");
        }, session));

        return eventStatus.build();
    }

    @Override
    public Collection<Event> get() {
        Session session = sessionFactory.openSession();
        Collection<Event> eventList = (Collection<Event>) session.query(Event.class, "MATCH (e:Event) RETURN e LIMIT 25", Collections.emptyMap());

        return eventList;

    }

    @Override
    public EventStatus deleteByNodeId(long eventId) {
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEvent(eventId, session);
        EventStatus.EventStatusBuilder eventStatus = EventStatus.builder();
        optionalEvent.ifPresentOrElse((event) -> {
            runInTransaction(() -> session.delete(event), session);
            eventStatus.status("Event with id " + eventId + " is deleted");
        }, () -> eventStatus.error(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build()));
        return eventStatus.build();

    }



    @Override
    public EventStatus updateByNodeId(long eventId, Event newEvent) {
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEvent(eventId, session);
        EventStatus.EventStatusBuilder eventStatus = EventStatus.builder();
        optionalEvent.ifPresentOrElse((event) -> {
            BeanUtils.copyProperties(newEvent, event, getNullPropertyNames(newEvent));
            runInTransaction(() -> session.save(event), session);
            eventStatus.status("Event with id " + eventId + " is updated");
        }, () -> eventStatus.error(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build()));
        return eventStatus.build();
    }

    private Optional<Event> getOptionalEvent(long eventId, Session session) {
        return Optional.ofNullable(session.load(Event.class, eventId));
    }

}
