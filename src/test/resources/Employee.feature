Feature: Employee Tests
  This feature includes tests the Employee RESTFul services

  Scenario: Create Employee
    Given I set employee service api endpoint
    When I set request HEADER
    And I Send POST http request
    Then the  verify post call response

  Scenario: Retrieve All Employee
    Given I set employee service api endpoint
    When I set request HEADER
    And I Send GET http request
    Then the  verify response code

  Scenario: Retrieve single Employee
    Given I set employee service api endpoint
    When I set request HEADER
    And I Send POST http request
    Given I set GET employee  http request with parameters
    Then the  verify response code


  Scenario: Update Employee
    Given I set employee service api endpoint
    And I Send POST http request
    And I Send PUT http request
    Then the  verify put call response
    Then the verify updated data in response

  Scenario: Delete Employee
    Given I set employee service api endpoint
    And I Send POST http request
    And I Send Delete http request
    Then the  verify delete response code
