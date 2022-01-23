package com.party.service;

import com.party.vo.SubEvent;
import com.party.vo.status.EventStatus;

public abstract class SubEventService implements IBaseService{
    public abstract EventStatus create(SubEvent event, Long eventId);

    public abstract EventStatus update(SubEvent subEvent, long id, long eventId);
}
