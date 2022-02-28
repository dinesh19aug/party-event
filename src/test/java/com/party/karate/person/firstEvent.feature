Feature: Get first event's id from all events
  Background:
    * url baseUrl

    Scenario: When get all event's first event id is extracted then return id
      Given url baseUrlSubevent
      And header Accept = 'application/json'
      And method GET
      Then print response
      * def eventId = response[0].id
      Then print eventId