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
import java.util.Optional;

@ApplicationScoped
@Named("personDelete")
public class PersonDelete implements IPersonService<PersonStatus>, IBaseService {
    SessionFactory sessionFactory;
    @Inject
    public PersonDelete(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public PersonStatus process(Object... args) {
        return deleteGuestDetails((long) args[0], (long) args[1]);
    }

    private PersonStatus deleteGuestDetails(long eventId, long personId) {
        Session session = sessionFactory.openSession();
        PersonStatus personStatus =new PersonStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse(event -> {
            Optional<Person> personSE = Optional.ofNullable(getPersonById(eventId, personId, session));
            personSE.ifPresentOrElse(person->{
                        runInTransaction(() -> deleteGuestById(eventId,personId, session) , session);

                        personStatus.setStatus("Person with id " + personId + " is deleted for eventId: ".concat(String.valueOf(eventId)));
                    },
                    ()->{
                        personStatus.setStatus("Failed to delete Person: " + personId);
                        EventError error = EventError.builder().errorDesc("Person not found").build();
                        personStatus.setError(error);
                    });
        },()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before deleting this guest").build();
            personStatus.setError(error);
        });
        return personStatus;


    }


}
