
package com.party.resource;

import com.party.service.IEventService;
import com.party.service.impl.EventService;
import com.party.vo.Event;
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dines
 */
@Path("/party/user")

public class EventResource {
    @Inject
    EventService eventService;
    
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(){
        Event event = eventService.execute();
        return Response.ok(event).build();
    }
}
