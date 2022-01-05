package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
public class ItemCost {
    private BigDecimal cost;
    private Currency currencyCode;
    private int qty;
    private String size;
    private float weight;
    private float length;
    private float width;
    private float height;
    private int hours;
    private float days;
    private float person;
}
