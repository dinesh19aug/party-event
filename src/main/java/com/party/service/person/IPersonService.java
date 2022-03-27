package com.party.service.person;

public interface IPersonService<T> {
    T process(Object... args);
}
