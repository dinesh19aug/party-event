package com.party.resource.person;

import com.party.service.person.IPersonService;
import com.party.vo.Person;
import com.party.vo.status.PersonStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonUpdateResource {
    @Named("personUpdate")
    IPersonService<PersonStatus> personService;

    @PUT
    @Path("/{event_id}/person/{person_id}")
    public PersonStatus updateEvent(@Valid @NotNull Person person, @PathParam("event_id") long eventId, @PathParam("person_id") long personId){

        return personService.process(person, eventId, personId);
    }
}
