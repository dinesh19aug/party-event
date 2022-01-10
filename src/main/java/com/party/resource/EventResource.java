
package com.party.resource;

import com.party.service.impl.EventService;
import com.party.vo.Event;
import com.party.vo.EventStatus;

import javax.inject.Inject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;

import java.util.Collection;

/**
 *
 * @author dines
 */
@Path("/party/event")

public class EventResource {
    @Inject
    EventService eventService;


    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EventStatus createEvent(@Valid @NotNull Event event){
        return eventService.create(event);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Collection<Event> getAllEvents(){
        return eventService.get();
    }

    @PUT
    @Path("{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EventStatus updateEvent(@PathParam("nodeId") long eventId, Event event){
        return eventService.updateByNodeId(eventId, event);
    }

    @DELETE
    @Path("{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EventStatus deleteOrderByNodeId(@PathParam("nodeId") long eventId){
        return  eventService.deleteByNodeId(eventId);
    }
    //Get All events by organizer email





}
