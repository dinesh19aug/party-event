Feature: Create a person for a given event
  Background:
    * url baseUrl
    * def signIn = call read('firstEvent.feature')
    * def personRequest = "{'firstName': 'Dinesh', 'lastName':'Arora', 'mobileNumber':'7044114108', 'emailAddress': 'test@test.com}"

    #http://localhost:8080/party/event/{eventId}/person}
    Scenario: Create a person for a given event id. Get a list of events --> Extract the eventid for the first event and create a person on that event
      Given path signIn.eventId + '/person/'
      And header Accept = 'application/json'
      And method POST
      And request personRequest
      * print personRequest
      #* print eventRequest
      Then  print response

