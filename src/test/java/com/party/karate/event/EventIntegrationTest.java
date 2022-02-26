package com.party.karate.event;

import com.intuit.karate.junit5.Karate;

class EventIntegrationTest {
    @Karate.Test
    Karate testCreateEvent(){
        return Karate.run("eventCreate").relativeTo(getClass());
    }
}
