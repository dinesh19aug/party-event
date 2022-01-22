package com.party.resource;

import com.party.service.SubEventService;
import com.party.service.impl.SubEventServiceImpl;
import com.party.vo.SubEvent;
import com.party.vo.status.EventStatus;
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
    public EventStatus createEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId){

        return subEventService.create(subEvent,eventId );

    }
}
