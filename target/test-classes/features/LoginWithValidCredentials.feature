@Login
Feature: Login with Valid Credentials

  As a user of the OrangeHRM website
  I want to login with valid credentials
  So that I can access my account-related features

  Background:
    Given I am on the login page

  @ValidLoginCredentials @SmokeTest @SanityTest @EndToEndTest @RegressionTest
  Scenario Outline: Login with Valid Credentials Should Navigate to Dashboard
    Given I enter a valid user name "<username>" and a valid password "<password>"
    When I click on the login button
    Then I should be logged in successfully and I should see the Dashboard page
    When I click on the logout button
    Then I should be successfully navigated to the Login page

    Examples:
      | username | password  |
      | Admin    | admin123  |