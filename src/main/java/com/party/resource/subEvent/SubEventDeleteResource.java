package com.party.resource.subEvent;

import com.party.service.subEvent.SubEventService;
import com.party.service.subEvent.SubEventServiceImpl;
import com.party.vo.status.SubEventStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubEventDeleteResource {
    SubEventService subEventService;
    public SubEventDeleteResource() {

    }

    @Inject
    public SubEventDeleteResource(SubEventServiceImpl s){
        this.subEventService=s;
    }





    @DELETE
    @Path("/{event_id}/subevent/{subEvent_id}")
    public SubEventStatus deleteSubEventById(@PathParam("event_id") long eventId, @PathParam("subEvent_id") long subEventId){
        return subEventService.deleteSubEventById(eventId, subEventId);
    }
    @DELETE
    @Path("/{event_id}/subevent")
    public SubEventStatus deleteAllSubeventByEventId(@PathParam("event_id") long eventId){
        return subEventService.deleteAllSubeventByEventId(eventId);
    }
}
