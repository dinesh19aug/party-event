
package com.party.resource.event;

import com.party.service.EventService;
import com.party.service.impl.event.EventServiceImpl;
import com.party.vo.Event;
import com.party.vo.status.EventStatus;

import javax.enterprise.context.RequestScoped;
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
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    EventService eventService;
    @Inject
    public EventResource(EventServiceImpl e){
        this.eventService = e;
    }

    @POST
    @Path("/")
    public EventStatus createEvent(@Valid @NotNull Event event){
        return eventService.create(event);
    }

    @GET
    @Path("/")
    public Collection<Event> getAllEvents(){
        return eventService.get();
    }

    @PUT
    @Path("{nodeId}")
    public EventStatus updateEvent(@PathParam("nodeId") long eventId, Event event){
        return eventService.updateByNodeId(eventId, event);
    }

    @DELETE
    @Path("{nodeId}")
    public EventStatus deleteOrderByNodeId(@PathParam("nodeId") long eventId){
        return  eventService.deleteByNodeId(eventId);
    }
    //Get All events by organizer email





}
