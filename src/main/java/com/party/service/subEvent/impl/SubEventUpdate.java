package com.party.service.subEvent.impl;

import com.party.service.IBaseService;
import com.party.service.subEvent.ISubEventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@ApplicationScoped
@Named("subEventUpdate")
public class SubEventUpdate implements ISubEventService<SubEventStatus>, IBaseService {
    SessionFactory sessionFactory;

    @Inject
    public SubEventUpdate(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public SubEventStatus process(Object... args) {
        return update((SubEvent) args[0],  (long) args[1],(long) args[2]);
    }

    /**
     * Check if event is present
     * get list of all subevents:
     * If subevent is present:
     * then update subevent
     * else:
     * return subenevr does not exist
     * else:
     * Return : Event does not exist
     *
     * @param newSubEvent SubEvent
     * @param eventId     Event Id
     * @param subEventId  SubEvent Id
     * @return EventStatus status
     */
    private SubEventStatus update(SubEvent newSubEvent, long eventId, long subEventId) {
        Session session = sessionFactory.openSession();
        SubEventStatus subEventStatus = new SubEventStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);

        optionalEvent.ifPresentOrElse((event -> {
            Optional<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId, session).stream().filter(se -> se.getId() == subEventId).findFirst();
            resultSE.ifPresentOrElse(oldSubEvent -> {
                BeanUtils.copyProperties(newSubEvent, oldSubEvent, getNullPropertyNames(newSubEvent));
                runInTransaction(() -> session.save(oldSubEvent), session);
                subEventStatus.setStatus("SubEvent with id " + subEventId + " is updated");
            }, () -> {
                subEventStatus.setStatus("Failed to update SubEvent: " + subEventId);
                EventError error = EventError.builder().errorDesc("SubEvent not found").build();
                subEventStatus.setError(error);
            });
        }), () -> {
            subEventStatus.setStatus("Failed to update SubEvent: " + subEventId);
            EventError error = EventError.builder().errorDesc("Event id:" + eventId + " does not exist").build();
            subEventStatus.setError(error);
        });

        return subEventStatus;
    }




}
