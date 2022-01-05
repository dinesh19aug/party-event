package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Item {
    private String taskItem;
    private String taskDescription;
    private BigDecimal cost;
    private float timeNeeded;
    private LocalDate startDate;
    private String endDate;
    private Enum<TaskItemStatus> taskStatus;
}
