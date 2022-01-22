package com.party.service;

import com.party.vo.Event;
import com.party.vo.status.EventStatus;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import java.lang.reflect.Field;
import java.util.*;


/**
 *
 * @author dines
 */

public abstract class EventService implements IBaseService{

    public abstract Collection<Event> get();

    public abstract EventStatus deleteByNodeId(long eventId);

    public abstract EventStatus updateByNodeId(long eventId, Event event);
    public abstract EventStatus create(Event event);
}
