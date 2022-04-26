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
import java.util.Collections;
import java.util.Optional;

@ApplicationScoped
@Named("personCreate")
public class PersonCreate implements IPersonService<PersonStatus>,  IBaseService {
    SessionFactory sessionFactory;
    @Inject
    public PersonCreate(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public PersonStatus process(Object... args) {
        return create((Person) args[0], (long) args[1]);
    }

    private PersonStatus create(Person person, long eventId) {
        Session session = sessionFactory.openSession();
        PersonStatus personStatus =new PersonStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse((event -> {
            //Check if person is already added
            Optional<Person> optionalPersonList = Optional.ofNullable(getPersonMatch(eventId, person, session));
            optionalPersonList.ifPresentOrElse((personList)->{
                        personStatus.setStatus("Person already exists for this eventId: " + eventId);
                    },()-> runInTransaction(() -> {
                        event.setPerson(Collections.singleton(person));
                        session.save(event);
                        personStatus.setStatus("Person added to event with id: " + eventId);
                    }, session));

        }), ()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before inviting new guest").build();
            personStatus.setError(error);
        });
        return personStatus;
    }
}

