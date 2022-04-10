package com.party.vo.status;

import com.party.vo.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class PersonStatus extends AStatus{
    List<Person> personList;
}
