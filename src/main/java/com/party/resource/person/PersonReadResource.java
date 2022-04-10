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
public class PersonReadResource {
    @Named("personRead")
    IPersonService<PersonStatus> personService;

    /**
     * Get all guests for an event
     */
    @GET
    @Path("/{event_id}/person")
    public PersonStatus getAllSubeventByEventId(@PathParam("event_id") long eventId) {
        return personService.process(eventId);

    }
}
