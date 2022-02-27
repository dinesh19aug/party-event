package com.party.resource.subEvent;

import com.party.service.SubEventService;
import com.party.service.impl.subEvent.SubEventServiceImpl;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubEventUpdateResource {

    SubEventService subEventService;
    public SubEventUpdateResource() {

    }

    @Inject
    public SubEventUpdateResource(SubEventServiceImpl s){
        this.subEventService=s;
    }


    @PUT
    @Path("/{event_id}/subevent/{subEvent_id}")
    public SubEventStatus updateEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId, @PathParam("subEvent_id") long subEventId){

        return subEventService.update(subEvent,eventId, subEventId );

    }


}
