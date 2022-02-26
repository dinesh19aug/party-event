Feature: Create event nodes

  Background: Create events and get first event id
    * url baseUrl
  * def eventRequest =
    """
    {
      "eventName":"My Integration test",
      "eventType": "TALK",
      "orgName": "Party Inc",
      "orgUrl":"www.org",
      "eventUrl":"www.event"
    }
    """
  Scenario: Given event details create a event node in Neo4j and you should not be able to creat the same event again
    Given path '/party/event'
    And header Accept = 'application/json'
    And request eventRequest
    And method POST
    * print eventRequest
    Then  print response
    And match response $.status == 'Event is created'
    And status 200
    Given url '/party/event'
    And header Accept = 'application/json'
    And request eventRequest
    When method POST
    * print eventRequest
    Then  print response
    And match response $.status == 'Event is not created'
    And status 200





    @Ignore
  Scenario: Clean up newly created scenario

