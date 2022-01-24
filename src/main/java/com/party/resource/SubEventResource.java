package com.party.resource;

import com.party.service.SubEventService;
import com.party.service.impl.SubEventServiceImpl;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
public class SubEventResource {


    SubEventService subEventService;
    @Inject
    public SubEventResource(SubEventServiceImpl subEventService){
        this.subEventService = subEventService;
    }

    /**
     * Create a subevent and attach to the provided event id
     * @param subEvent Sub event
     * @return EventStatus
     */
    @POST
    @Path("/{event_id}/subevent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SubEventStatus createEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId){

        return subEventService.create(subEvent,eventId );

    }


    @PUT
    @Path("/{event_id}/subevent/{subEvent_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SubEventStatus updateEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId, @PathParam("subEvent_id") long subEventId){

        return subEventService.update(subEvent,eventId, subEventId );

    }

    /**
     * Get all sub events for a given event id
     * @param eventId Event id
      * @return SubEventStatus Status
     */
    @GET
    @Path("/{event_id}/subevent")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SubEventStatus updateEvent(@PathParam("event_id") long eventId){
        return subEventService.getAllSubEvents(eventId);

    }
    //TODO Delete SubEvent id for a given Event id
    //TODO Delete all subevent for a given Event Id
    //TODO Get Subevent by id for given event id
}
