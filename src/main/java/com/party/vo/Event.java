
package com.party.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author dinesh
 */
@Data
@AllArgsConstructor
public class Event {
  private String name;
  private Enum<Type> eventType;
  private  String orgName;
  private String orgUrl;
  private String eventUrl;

}
