package com.party.service.subEvent;

import com.party.service.IBaseService;
import com.party.vo.status.SubEventStatus;

public abstract class SubEventService implements IBaseService {


    public abstract SubEventStatus deleteSubEventById(long eventId, long subEventId);

    public abstract SubEventStatus deleteAllSubeventByEventId(long eventId);


}
