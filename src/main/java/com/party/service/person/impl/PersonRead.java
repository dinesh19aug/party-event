package com.party.service.person.impl;

import com.party.service.IBaseService;
import com.party.service.person.IPersonService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.Person;
import com.party.vo.status.PersonStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("personRead")
public class PersonRead  implements IPersonService<PersonStatus>, IBaseService {
    SessionFactory sessionFactory;
    @Inject
    public PersonRead(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public PersonStatus process(Object... args) {
        return getListOfGuests( (long) args[0]);
    }

    private PersonStatus getListOfGuests(long eventId) {
        Session session = sessionFactory.openSession();
        PersonStatus personStatus =new PersonStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse(event -> {
            List<Person> guestList = getAllGuestByEventId(session, eventId);
            personStatus.setPersonList(guestList);
            personStatus.setStatus("Found: "+ (long) guestList.size() + " guests");
        },()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before inviting new guest").build();
            personStatus.setError(error);
        });
        return personStatus;
    }


}
