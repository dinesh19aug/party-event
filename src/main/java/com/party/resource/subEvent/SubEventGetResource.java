package com.party.resource.subEvent;

import com.party.service.SubEventService;
import com.party.service.impl.SubEventServiceImpl;
import com.party.vo.status.SubEventStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/party/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubEventGetResource {

    SubEventService subEventService;
public SubEventGetResource() {

        }

@Inject
public SubEventGetResource(SubEventServiceImpl s){
        this.subEventService=s;
        }




/**
 * Get all sub events for a given event id
 * @param eventId Event id
 * @return SubEventStatus Status
 */
@GET
@Path("/{event_id}/subevent")
public SubEventStatus updateEvent(@PathParam("event_id") long eventId){
        return subEventService.getAllSubEvents(eventId);

        }

    /*@GET
    @Path("/{event_id}/subevent/{subEvent_id}")
    public SubEventStatus*/
        }
