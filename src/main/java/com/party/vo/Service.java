package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Service {
   private String serviceDescription;
   private BigDecimal fixedCost;

}
