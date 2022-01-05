
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
public class Person {
    private String firstName;
    private String lastName;
    private String MobileNumber;
    private String emailAddress;
}
