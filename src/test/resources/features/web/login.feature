@web
Feature: Login functionality

  Scenario: Login with valid credentials
    Given I am on the login page
    When I login with username "admin" and password "admin"
    Then I should see the home page
