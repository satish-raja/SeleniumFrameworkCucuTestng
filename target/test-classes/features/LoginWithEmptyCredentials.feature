@Login
Feature: Login with Empty Credentials

  As a user of the OrangeHRM website
  I want to see error messages when I leave the username and password fields empty
  So that I know to fill in the required fields

  Background:
    Given I am on the login page

  @EmptyLoginCredentials @SanityTest
  Scenario: Login with Empty Credentials Should Display Error Messages
    Given I leave the username and password fields empty
    When I click on the login button
    Then I should see error messages for blank username and password fields
