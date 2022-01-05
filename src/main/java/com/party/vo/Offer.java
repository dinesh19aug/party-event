package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Offer {
    private String offerDescription;
    private String couponCode;
    private int discountPercentage;

}
