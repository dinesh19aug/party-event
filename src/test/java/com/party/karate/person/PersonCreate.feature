Feature: Create a person for a given event
  Background:
    * url baseUrlSubevent

    Scenario: Create a person for a given event id. Get a list of events --> Extract the eventid for the first event and create a person on that event
      Given path