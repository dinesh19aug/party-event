Feature: Get first event's id from all events
  Background:
    * url baseUrl
    * def eventId = 0
    Scenario: When get all event's first event id is extracted then return id
      Given url baseUrl
      And header Accept = 'application/json'
      And method GET
      Then print response
      Then def eventId = response[1].id
