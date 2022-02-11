package com.party.service;

import com.party.vo.SubEvent;
import com.party.vo.status.SubEventStatus;

public abstract class SubEventService implements IBaseService{
    public abstract SubEventStatus create(SubEvent event, Long eventId);

    public abstract SubEventStatus update(SubEvent subEvent, long id, long eventId);

    public abstract SubEventStatus getAllSubEvents(long eventId);

    public abstract SubEventStatus deleteSubEventById(long eventId, long subEventId);

    public abstract SubEventStatus deleteAllSubeventByEventId(long eventId);
}
