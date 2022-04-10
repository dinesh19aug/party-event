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

    IPersonService<PersonStatus> personService;

    IPersonService<PersonStatus> personDetailService;

    public PersonReadResource(@Named("personReadAll") IPersonService<PersonStatus> ps, @Named("personDetail") IPersonService<PersonStatus> pds){
        this.personService = ps;
        this.personDetailService = pds;
    }

    /**
     * Get all guests for an event
     * @param eventId Id of the event
     * @return PersonStatus
     */
    @GET
    @Path("/{event_id}/person")
    public PersonStatus getAllGuestsByEventId(@PathParam("event_id") long eventId) {
        return personService.process(eventId);
    }

    /**
     * Get guest details of a given event
      * @param eventId Event Id
     * @param personId Person id
     * @return PersonStatus
     */
    @GET
    @Path("/{event_id}/person/{person_id}")
    public PersonStatus getAllGuestsByEventId(@PathParam("event_id") long eventId, @PathParam("person_id") long personId) {
        return personDetailService.process(eventId, personId);

    }
}
