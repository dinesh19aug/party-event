package com.party.resource.person;

import com.party.service.person.IPersonService;
import com.party.service.person.impl.PersonCreate;
import com.party.vo.Person;
import com.party.vo.status.PersonStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonCreateResource {
    IPersonService<PersonStatus> personService;

    public PersonCreateResource() {

    }

    @Inject
    @Named("personCreate")
    public PersonCreateResource(PersonCreate s){
        this.personService=s;
    }

    /***
     * Creates a person for a given event Id
     * @param person Person object
     * @param eventId Long event id
     * @return PersonStatus object
     */
    @POST
    @Path("/{event_id}/person")
    public PersonStatus createPerson(@Valid @NotNull Person person, @PathParam("event_id") long eventId){
        return personService.process(person,eventId );
    }

}
