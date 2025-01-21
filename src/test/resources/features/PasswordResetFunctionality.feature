@Login
Feature: Password Reset Functionality

  As a user of the OrangeHRM website
  I want to reset my password if I forget it
  So that I can regain access to my account

  Background:
    Given I am on the login page

  @LoginResetPassword @SanityTest
  Scenario: Reset Password Functionality Should Display Success Message
    When I click on the "Forgot your password?" link
    Then I should be redirected to the Reset Password page
    And I enter a valid username "Admin"
    When I click on the Reset Password button
    Then I should see a message "Reset Password link sent successfully"
