Feature: Login CRUD

  Background: Common setup for scenarios
    Given Setup base request specification without auth

  Scenario: L01 Providing valid credentials at login endpoint user should be logged in
    When User sends a valid POST request to Login as existing user
    Then Response status should be 200
     And Created user should have Access Token

  Scenario: L02 Providing wrong password at login endpoint user should not be logged in
    Given Login request is updated with
      | email             | password      |
      | testUser@mail.com | wrongPassword |
    When User sends a not valid POST request to Login
    Then Response status should be 400
     And Body response should contain text "Incorrect password"

  Scenario: L03 Providing wrong email at login endpoint user should not be logged in
    Given Login request is updated with
      | email            | password    |
      | noUser@gmail.com | password123 |
    When User sends a not valid POST request to Login
    Then Response status should be 400
     And Body response should contain text "Cannot find user"

  Scenario: L04 Providing no email at login endpoint user should not be logged in
    Given Login request is updated with
      | password    |
      | password123 |
    When User sends a not valid POST request to Login
    Then Response status should be 400
     And Body response should contain text "Email and password are required"

  Scenario: L05 Providing no password at login endpoint user should not be logged in
    Given Login request is updated with
      | email             |
      | testUser@mail.com |
    When User sends a not valid POST request to Login
    Then Response status should be 400
     And Body response should contain text "Email and password are required"
