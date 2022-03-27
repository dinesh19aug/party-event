package com.party.resource.subEvent;

import com.party.service.subEvent.ISubEventService;
import com.party.service.subEvent.impl.SubEventCreate;
import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;

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
public class SubEventCreateResource {


    ISubEventService<SubEventStatus> subEventService;
    public SubEventCreateResource() {

    }

    @Inject
    @Named("subEventCreate")
    public SubEventCreateResource(SubEventCreate s){
        this.subEventService=s;
    }



    /**
     * Create a subevent and attach to the provided event id
     * @param subEvent Sub event
     * @return EventStatus
     */
    @POST
    @Path("/{event_id}/subevent")
    public SubEventStatus createEvent(@Valid @NotNull SubEvent subEvent, @PathParam("event_id") long eventId){

        return subEventService.process(subEvent,eventId );

    }





}
