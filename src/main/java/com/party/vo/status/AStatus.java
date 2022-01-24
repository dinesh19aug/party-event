package com.party.vo.status;

import com.party.vo.EventError;
import lombok.Data;

@Data
public class AStatus {
    public String status;
    public EventError error;
}
