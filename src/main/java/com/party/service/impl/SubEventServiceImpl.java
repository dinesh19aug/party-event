package com.party.service.impl;

import com.party.service.SubEventService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped

public class SubEventServiceImpl extends SubEventService {
//TODO Break this into single services. Every change in this class for new services means retesting all methods. Breaks SOLID principle
    public SubEventServiceImpl(){}

    SessionFactory sessionFactory;

    @Inject
    public SubEventServiceImpl(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public SubEventStatus create(SubEvent subEvent, Long eventId) {
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
    public SubEventStatus update(SubEvent newSubEvent, long eventId, long subEventId) {
        Session session = sessionFactory.openSession();
        SubEventStatus subEventStatus =new  SubEventStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);

        optionalEvent.ifPresentOrElse((event -> {
                    Optional<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId,session).stream().filter(se -> se.getId() == subEventId).findFirst();
                    resultSE.ifPresentOrElse(oldSubEvent -> {
                                BeanUtils.copyProperties(newSubEvent, oldSubEvent, getNullPropertyNames(newSubEvent));
                                runInTransaction(() -> session.save(oldSubEvent), session);
                        subEventStatus.setStatus("SubEvent with id " + subEventId + " is updated");
                            },() -> {
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

    @Override
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

    /**
     * Delete subevent by subeventId for a given eventId
     * @param eventId Event Id
     * @param subEventId Subevent Id
     * @return SubEventStatus
     */
    @Override
    public SubEventStatus deleteSubEventById(long eventId, long subEventId) {
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);

        optionalEvent.ifPresentOrElse((event) -> {
            Optional<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId,session).stream().filter(se -> se.getId() == subEventId).findFirst();
            resultSE.ifPresentOrElse(subEventTobeDeleted -> {
               //Run delete
                runInTransaction(()->{
                   /* session.query(SubEvent.class,"MATCH(e:Event)-[r]->(s:SubEvent)\n" +
                            "WHERE ID(e)=$eventId AND ID(s)=$subeventId \n" +
                            "DETACH DELETE s", Map.of("eventId", eventId, "subeventId", subEventId));*/
                    session.delete(subEventTobeDeleted);
                    subEventStatus.setStatus("SubEvent: ".concat(String.valueOf(subEventId)).concat(" is deleted for eventId: ").concat(String.valueOf(eventId)));
                }, session);
            },() -> {// Subevent not found
                subEventStatus.setStatus("Failed to delete SubEvent: " + subEventId);
                EventError error = EventError.builder().errorDesc("SubEvent not found").build();
                subEventStatus.setError(error);
            });

            //Event not found
        }, () -> subEventStatus.setError(EventError.builder().errorDesc("Event with id " + eventId +" subEvent Id: "+ subEventId + " is not found").build()));
        return subEventStatus;

    }

    @Override
    public SubEventStatus deleteAllSubeventByEventId(long eventId) {
        SubEventStatus subEventStatus = new SubEventStatus();
        Session session = sessionFactory.openSession();
        Optional<Event> optionalEvent = getOptionalEventById(eventId, session);
        optionalEvent.ifPresentOrElse((event -> {
                    List<SubEvent> resultSE = getOptionalSubEventBySubEventId(eventId,session);
                    for(SubEvent s: resultSE){
                        runInTransaction(()->session.delete(s),session) ;
                    }
                    subEventStatus.setStatus("All Subevents deleted for Event: ".concat(String.valueOf(eventId)));
                })
                , () ->{//Event not found
                    subEventStatus.setError(EventError.builder().errorDesc("Event with id " + eventId + " is not found").build());
                });
        return subEventStatus;
    }

    @Override
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


    private Optional<Event> getOptionalEventById(long eventId, Session session) {
        return Optional.ofNullable(session.load(Event.class, eventId));
    }

    private List<SubEvent> getOptionalSubEventBySubEventId(long eventId, Session session){
        return (List<SubEvent>) session.query(SubEvent.class,"MATCH (e:Event )-[:HAS_SUBEVENT]->(s:SubEvent) WHERE ID(e)=" + eventId + " RETURN s", Collections.emptyMap());
    }

}
