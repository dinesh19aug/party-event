package com.party.service.event;

import com.party.service.IBaseService;
import com.party.vo.Event;
import com.party.vo.status.EventStatus;

import java.util.Collection;


/**
 *
 * @author dines
 */

public abstract class EventService implements IBaseService {

    public abstract Collection<Event> get();

    public abstract EventStatus deleteByNodeId(long eventId);

    public abstract EventStatus updateByNodeId(long eventId, Event event);
    public abstract EventStatus create(Event event);
}
