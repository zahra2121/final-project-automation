@api
Feature: User API
  @api
  Scenario Outline: Get user by valid ID
    Given I set base API endpoint
    When I send GET request to user by ID "<id>"
    Then the response status code should be <statusCode>
    And response should contain "<field>"
    Examples:
      | id                         | statusCode | field       |
      | 60d0fe4f5311236168a109e2  | 200        | firstName   |


  @api
  Scenario Outline: Create new user
    Given I set base API endpoint
    When I send POST request to create user with firstName "<firstName>", lastName "<lastName>", email "<email>"
    Then the response status code should be <statusCode>
    And response should contain "<field>"
    Examples:
      | firstName | lastName | email                       | statusCode | field |
      | Jeruk     | Alan    | jerukalana1123@example.com   | 200        | id    |


  @api
  Scenario Outline: Update user by ID
    Given I set base API endpoint
    When I send PUT request to user ID "<id>" with name "<firstName>"
    Then the response status code should be <statusCode>
    And response should contain "<field>"
    Examples:
      | id                       | firstName           | statusCode | field     |
      | 60d0fe4f5311236168a109e5 | Updatedisini        | 200        | firstName |


  @api
  Scenario Outline: Delete user by ID
    Given I set base API endpoint
    When I send DELETE request to user by ID "<id>"
    Then the response status code should be <statusCode>
    Examples:
      | id                       | statusCode |
      | 60d0fe4f5311236168a109d5 | 200        |

  @api @negative
  Scenario Outline: Get user with invalid ID
    Given I set base API endpoint
    When I send GET request to user by ID "<id>"
    Then the response status code should be <statusCode>
    Examples:
      | id           | statusCode |
      | 99999999aaaa | 404        |
