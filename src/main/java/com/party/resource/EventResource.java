
package com.party.resource;

import com.party.service.impl.EventService;
import com.party.vo.Event;
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.core.Response;

import com.party.vo.Movie;
import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;

import java.util.concurrent.CompletionStage;

/**
 *
 * @author dines
 */
@Path("/party")

public class EventResource {
    @Inject
    EventService eventService;
    @Inject
    Driver driver;
    @GET
    @Path("event")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(){
        Event event = eventService.execute();
        return Response.ok(event).build();
    }
    @GET
    @Path("movie")
    public CompletionStage<Response> getMovie(){
        AsyncSession session = driver.asyncSession();
        return session
                .runAsync("MATCH (m:Movie) RETURN m ORDER BY m.title")
                .thenCompose(cursor ->
                        cursor.listAsync(record -> Movie.from(record.get("m").asNode()))
                )
                .thenCompose(movies ->
                        session.closeAsync().thenApply(signal -> movies)
                )
                .thenApply(Response::ok)
                .thenApply(Response.ResponseBuilder::build);
    }
}
