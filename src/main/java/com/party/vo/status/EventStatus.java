package com.party.vo.status;

import com.party.vo.EventError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class EventStatus {
    private String status;
    private EventError error;
}
