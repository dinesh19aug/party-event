package com.party.service.impl;

import com.party.service.SubEventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.EventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public final class SubEventServiceImpl extends SubEventService {

    @Inject
    SessionFactory sessionFactory;

    //TODO Search the event by event id and save the event.setSubevent(subEvent) - Ref: https://neo4j.com/docs/ogm-manual/current/reference/#reference:session:basic-operations
    @Override
    public EventStatus create(SubEvent subEvent, Long eventId) {
        Session session = sessionFactory.openSession();
        EventStatus eventStatus = EventStatus.builder().build();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse((event -> {
               Optional<List<SubEvent>> optionalSubEventList = Optional.ofNullable(event.getSubEvent());
               optionalSubEventList.ifPresentOrElse((subEventList)->{
                        subEventList.add(subEvent);
                           runInTransaction(() -> {
                               event.setSubEvent(subEventList);
                               session.save(event);
                               eventStatus.setStatus("SubEvent added to event with id: " + eventId);
                           }, session);
                       }
                       ,()-> runInTransaction(() -> {
                           event.setSubEvent(Collections.singletonList(subEvent));
                           session.save(event);
                           eventStatus.setStatus("SubEvent added to event with id: " + eventId);
                       }, session));

        }), ()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before adding subEvents").build();
            eventStatus.setError(error);
        });
        return eventStatus;
    }

    /**
     * Check if event is present
     *  get list of all subevents:
     *      If subevent is present:
     *          then update subevent
     *      else:
     *          return subenevr does not exist
     * else:
     *  Return : Event does not exist
     *
     * @param newSubEvent SubEvent
     * @param eventId Event Id
     * @param subEventId SubEvent Id
     * @return EventStatus status
     */
    @Override
    public EventStatus update(SubEvent newSubEvent, long eventId, long subEventId) {
        Session session = sessionFactory.openSession();
        EventStatus eventStatus = EventStatus.builder().build();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        //If event present
        optionalEvent.ifPresentOrElse((event -> {
                                                    Optional<SubEvent> resultSE = event.getSubEvent().stream().filter(se -> se.getId() == subEventId).findFirst();
                                                    resultSE.ifPresentOrElse(oldSubEvent -> {
                                                                                                BeanUtils.copyProperties(newSubEvent, oldSubEvent, getNullPropertyNames(newSubEvent));
                                                                                                runInTransaction(() -> session.save(event), session);
                                                                                                eventStatus.setStatus("SubEvent with id " + subEventId + " is updated");
                                                                                             },
                                                                                        () -> {
                                                                                                eventStatus.setStatus("Failed to update SubEvent: " + subEventId);
                                                                                                EventError error = EventError.builder().errorDesc("SubEvent not found").build();
                                                                                                eventStatus.setError(error);
                                                                                               }
                                                                               );
                                               }
                                       ),
                                        () -> {
                                                eventStatus.setStatus("Failed to update SubEvent: " + subEventId);
                                                EventError error= EventError.builder().errorDesc("Event id:" + eventId + " does not exist").build();
                                                eventStatus.setError(error);
                                               }
                                               );

        return eventStatus;
    }


    private Optional<Event> getOptionalEventById(long eventId, Session session) {
        return Optional.ofNullable(session.load(Event.class, eventId));
    }
}
