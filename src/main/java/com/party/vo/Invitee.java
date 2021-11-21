
package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author dines
 */
@Data
@AllArgsConstructor
public class Invitee {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email_address;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String rsvp;
}
