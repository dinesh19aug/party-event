Feature: Test all Subevent CRUD
  Background:
    * url baseUrlSubevent


    Scenario: Get all Event --> extract the first event id --> get all Subevents for eventId --> get first subeventId --> Get details of subEvent by subeventId
      Given url baseUrlSubevent
      And header Accept = 'application/json'
      And method GET
      Then print response
      * def eventId = response[0].id
      Then print eventId

      Given path eventId + '/subevent/'
      And header Accept = 'application/json'
      And method GET
      Then print response
      * def subEventId = response.subEvents[0].id
      And print subEventId

      Given path eventId + '/subevent/' + subEventId
      And header Accept = 'application/json'
      And method GET
      Then print response
      And match response.subEvents == '#[1]'


