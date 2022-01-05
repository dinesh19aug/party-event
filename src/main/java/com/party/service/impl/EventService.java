package com.party.service.impl;

import com.party.service.IEventService;
import com.party.vo.Event;
import com.party.vo.Person;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author dines
 */
@ApplicationScoped
public class EventService implements IEventService{

    @Override
    public Event execute() {
        List<Person> inviteeList = getInviteeList();
        
        Event event = new Event("Dinesh@gmail.com", inviteeList);
                
        return event;
    }

    private List<Person> getInviteeList() {
        
        List<Person> inviteeList = new ArrayList();
        Person invite = new Person("Dinesh", "Arora","704-488-5833",
        "dinesh@gmail.com","1322 Middlecrest DR Nw","Concord", "NC","28027", "Y");
        inviteeList.add(invite);
        invite = new Person("Deepti", "Chavan","704-490-6503",
        "deepti@gmail.com","1322 Middlecrest DR Nw","Concord", "NC","28027", "Y");
        inviteeList.add(invite);
        return inviteeList;
    }

    
   
}
