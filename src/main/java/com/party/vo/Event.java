
package com.party.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author dines
 */
@Data
@AllArgsConstructor
public class Event {
  private String organizer_email;
  private List<Invitee> inviteeList;  
}
