package com.party.service.impl;

import com.party.service.IEventService;
import com.party.vo.Event;

import java.util.*;

import com.party.vo.EventError;
import com.party.vo.EventStatus;

import org.neo4j.ogm.session.Session;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author dines
 */
@ApplicationScoped
public class EventService implements IEventService {
    @Inject
    SessionFactory sessionFactory;

    //TODO When the event does not exist create Event else throw error
    public EventStatus create(Event event) {
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = Optional.ofNullable(session.load(Event.class, event.getName()));
        EventStatus.EventStatusBuilder eventStatus = EventStatus.builder();
        optionalEvent.ifPresentOrElse((e) -> {
            eventStatus.status("Event is not created");
            eventStatus.error(EventError.builder().errorDesc("Event with id " + e.getName() + " already exists").build());
        }, () -> {
            runInTransaction(() -> {
                session.save(event);
                eventStatus.status("Event is created");
            }, session);

        });
        return eventStatus.build();
    }

    @Override
    public Collection<Event> get() {
        Session session = sessionFactory.openSession();
        //ArrayList<Event> eventList = new ArrayList<Event>(session.loadAll(Event.class));
        Collection<Event> eventList = (Collection<Event>) session.query(Event.class, "MATCH (e:Event) RETURN e LIMIT 25", Collections.emptyMap());
        //Collection<Event> eventList = session.loadAll(Event.class);
        return eventList;

    }

    @Override
    public EventStatus deleteByNodeId(long eventId) {
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = Optional.ofNullable(session.load(Event.class, eventId));
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
        Optional<Event> optionalEvent = Optional.ofNullable(session.load(Event.class, eventId));
        EventStatus.EventStatusBuilder eventStatus = EventStatus.builder();
        optionalEvent.ifPresentOrElse((event) -> {
            BeanUtils.copyProperties(newEvent, event, getNullPropertyNames(newEvent));
            runInTransaction(() -> session.save(event), session);
            eventStatus.status("Event with id " + eventId + " is updated");
        }, () -> eventStatus.error(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build()));
        return eventStatus.build();
    }

}
