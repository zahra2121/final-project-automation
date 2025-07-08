@api
Feature: Tag API

  @api
  Scenario: Get all tags
    Given I set base API endpoint
    When user requests all tags
    Then response status code should be 200
