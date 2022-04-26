package com.party.resource.person;

import com.party.service.person.IPersonService;
import com.party.vo.status.PersonStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PersonDeleteResource {
    @Named("personDelete")
    IPersonService<PersonStatus> personService;

    /**
     * Delete a person with person id for a given event
     * @param eventId Event id
     * @param personId Person id
     * @return PersonStatus Person Status object
     */
    @DELETE
    @Path("/{event_id}/person/{person_id}")
    public PersonStatus updateEvent( @PathParam("event_id") long eventId, @PathParam("person_id") long personId){

        return personService.process(eventId, personId);
    }
}
