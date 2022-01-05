package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Task {
    private String taskName;
    private String taskDescription;
    private float totalHoursSpent;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalCost;
}
