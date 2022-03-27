package com.party.resource.subEvent;

import com.party.service.subEvent.SubEventService;
import com.party.service.subEvent.ISubEventService;
import com.party.service.subEvent.SubEventServiceImpl;
import com.party.vo.status.SubEventStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubEventGetResource {

    SubEventService subEventService;
    @Inject
    @Named("getSubEventById")
    ISubEventService<SubEventStatus> subEventIdService;
    @Inject
    @Named("getAllSubEvents")
    ISubEventService<SubEventStatus> subEventAllService;

    public SubEventGetResource() {

    }

    @Inject
    public SubEventGetResource(SubEventServiceImpl s) {
        this.subEventService = s;
    }


    /**
     * Get all sub events for a given event id
     *
     * @param eventId Event id
     * @return SubEventStatus Status
     */
    @GET
    @Path("/{event_id}/subevent")
    public SubEventStatus getAllSubeventByEventId(@PathParam("event_id") long eventId) {
        return subEventAllService.process(eventId);

    }

    @GET
    @Path("/{event_id}/subevent/{subEvent_id}")
    public SubEventStatus getSubEventById(@PathParam("event_id") Long eventId, @PathParam("subEvent_id") Long subEventId) {
        return subEventIdService.process(eventId, subEventId);

    }

}
