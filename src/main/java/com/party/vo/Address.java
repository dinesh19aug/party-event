package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale.IsoCountryCode;

@AllArgsConstructor
@Data
@NodeEntity("Address")
public class Address {
    private String addressLine_1;
    private String addressLine_2;
    private String roomNumber;
    private String floorNumber;
    private String suiteNumber;
    private String city;
    private String state;
    private String zipCode;
    private IsoCountryCode countryCode;
}
