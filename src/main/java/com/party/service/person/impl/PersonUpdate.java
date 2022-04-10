package com.party.service.person.impl;

import com.party.service.IBaseService;
import com.party.service.person.IPersonService;
import com.party.vo.Event;
import com.party.vo.EventError;
import com.party.vo.Person;
import com.party.vo.status.PersonStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@ApplicationScoped
@Named("PersonUpdate")
public class PersonUpdate implements IPersonService<PersonStatus>, IBaseService {
    SessionFactory sessionFactory;
    @Inject
    public PersonUpdate(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public PersonStatus process(Object... args) {
        return updateGuestDetails((Person) args[0], (long) args[1], (long) args[2]);
    }

    private PersonStatus updateGuestDetails(Person newPerson, long eventId, long personId) {
        Session session = sessionFactory.openSession();
        PersonStatus personStatus =new PersonStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse(event -> {
            Optional<Person> personSE = Optional.ofNullable(getPersonById(eventId, personId, session));
            personSE.ifPresentOrElse(oldPerson->{
                        BeanUtils.copyProperties(newPerson, oldPerson, getNullPropertyNames(newPerson));
                        runInTransaction(() -> session.save(oldPerson), session);
                        personStatus.setStatus("Person with id " + personId + " is updated");
                    },
                    ()->{
                        personStatus.setStatus("Failed to update Person: " + personId);
                        EventError error = EventError.builder().errorDesc("Person not found").build();
                        personStatus.setError(error);
                    });
        },()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before inviting new guest").build();
            personStatus.setError(error);
        });
        return personStatus;
    }


}
