package com.party.service.impl;

import com.party.service.IEventService;
import com.party.vo.Event;
import com.party.vo.Person;

import com.party.vo.Type;
import org.neo4j.ogm.session.Session;

import org.neo4j.ogm.session.SessionFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dines
 */
@ApplicationScoped
public class EventService implements IEventService{
    @Inject
    SessionFactory sessionFactory;
    @Override
    public Event execute() {
        List<Person> inviteeList = getInviteeList();
        
        Event event = null;//new Event("Dinesh@gmail.com", inviteeList);
                
        return event;
    }

    private List<Person> getInviteeList() {
        
        List<Person> inviteeList = new ArrayList();
        /*Person invite = new Person("Dinesh", "Arora","704-488-5833",
        "dinesh@gmail.com","1322 Middlecrest DR Nw","Concord", "NC","28027", "Y");
        inviteeList.add(invite);
        invite = new Person("Deepti", "Chavan","704-490-6503",
        "deepti@gmail.com","1322 Middlecrest DR Nw","Concord", "NC","28027", "Y");
        inviteeList.add(invite);*/
        return inviteeList;
    }


    public void create(Event event) {
       Session session = sessionFactory.openSession();
        runInTransaction(()->{
            Event newEvent = new Event(event.getName(), event.getEventType(), event.getName(), event.getOrgUrl(), event.getEventUrl(),null);
            session.save(newEvent);
        }, session);

    }
}
