package com.party.vo.status;

import com.party.vo.SubEvent;
import lombok.Data;

import java.util.List;

@Data
public class SubEventStatus extends AStatus{
    List<SubEvent> subEvents;
}
