package com.party.resource.subEvent;

import com.party.service.impl.subEvent.ISubEventService;
import com.party.service.impl.subEvent.impl.SubEventUpdate;
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

     ISubEventService subEventUpdate;
    public SubEventUpdateResource() {

    }

    @Inject
    public SubEventUpdateResource(SubEventUpdate s){
        this.subEventUpdate=s;
    }


    @PUT
    @Path("/{event_id}/subevent/{subEvent_id}")
    public SubEventStatus updateEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId, @PathParam("subEvent_id") long subEventId){

        return (SubEventStatus) subEventUpdate.process(subEvent,eventId, subEventId );

    }


}
