@Login
Feature: Login with Invalid Credentials

  As a user of the OrangeHRM website
  I want to see error messages when I enter invalid credentials
  So that I can correct my mistakes and login successfully

  Background:
    Given I am on the login page

  @InvalidLoginCredentials @SanityTest @RegressionTest
  Scenario Outline: Login with Invalid Credentials Should Display Error Message
    Given I enter an invalid user name "<username>" and an invalid password "<password>"
    When I click on the login button
    Then I should see an error message "Invalid credentials" on the Login page

    Examples:
      | username | password  |
      | Admin    | admin     |
      | Admin   | admin123  |
      | Adminn   | admin     |
