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
@Named("personDetail")
public class PersonDetail implements IPersonService<PersonStatus>, IBaseService {
    SessionFactory sessionFactory;
    @Inject
    public PersonDetail(SessionFactory s){
        this.sessionFactory = s;
    }

    @Override
    public PersonStatus process(Object... args) {
        return getPersonDetails( (long) args[0], (long) args[1]);
    }

    private PersonStatus getPersonDetails(long eventId, long personId) {
        Session session = sessionFactory.openSession();
        PersonStatus personStatus =new PersonStatus();
        Optional<Event> optionalEvent = getOptionalEventById(eventId,session);
        optionalEvent.ifPresentOrElse(event -> {
            Optional<Person> personOptional = Optional.ofNullable(getPersonById(eventId, personId, session));
            personOptional.ifPresentOrElse(person -> {
                        personStatus.setStatus("Personal detail found for eventId ".concat(String.valueOf(eventId)).concat( " and person id: ").concat(String.valueOf(personId)));
                        personStatus.setPersonList(Collections.singletonList(person));
                    },
                    ()->{
                        //Person not found
                        personStatus.setStatus("Personal detail with personId: ".concat(String.valueOf(personId)).concat(" not found"));
                    });

        },()->{
            //Event is not found
            EventError error = EventError.builder().errorDesc("Event Id: " + eventId + " does not exist. Please check eventID before inviting new guest").build();
            personStatus.setError(error);
        });
        return personStatus;
    }

}
