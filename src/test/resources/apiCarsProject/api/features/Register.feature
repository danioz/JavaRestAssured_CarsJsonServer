Feature: Registration CRUD

  Background: Common setup for scenario
    Given Setup base request specification without auth

  Scenario: R01 Providing new user data at register endpoint should user be created
    When Register request is updated with random data
     And User sends a valid POST request to Register
    Then Response status should be 201
     And Created user should have Access Token

  Scenario: R02 Providing existing email at register endpoint should user not be created
    When User sends a valid POST request to Register
    Then Response status should be 400
     And Body response should contain text "Email already exists"

  Scenario: R03 Providing no user data at register endpoint should user not be created
    When Register request is updated with
      | email | password |
      |       |          |
    When User sends a valid POST request to Register
    Then Response status should be 400
     And Body response should contain text "Email and password are required"

  Scenario: R04 Providing only email at register endpoint should user not be created
    When Register request is updated with
      | email              |
      | danio123@gmail.com |
    When User sends a valid POST request to Register
    Then Response status should be 400
     And Body response should contain text "Email and password are required"

  Scenario: R05 Providing only password at register endpoint should user not be created
    When Register request is updated with
      | password  |
      | password1 |
    When User sends a valid POST request to Register
    Then Response status should be 400
     And Body response should contain text "Email and password are required"

  Scenario Outline: R06 (3-5) Enter not valid credentials
    When Register request is updated with <email> and <password>
     And User sends a valid POST request to Register
    Then Response status should be 400
     And Body response should contain text <message>

    Examples:
      | email             | password  | message                           |
      |                   |           | "Email and password are required" |
      |                   | password1 | "Email and password are required" |
      | testUser@mail.com |           | "Email and password are required" |

  Scenario Outline: R07 Enter invalid email format
    When Register request is updated with <email> and <password>
    And User sends a valid POST request to Register
    Then Response status should be 400
    And Body response should contain text <message>

    Examples:
      | email             | password  | message                           |
      | testUsermail.com  | password1 | "Email format is invalid"         |
      | testUser@         | password1 | "Email format is invalid"         |
      | testUser          | password1 | "Email format is invalid"         |
      | @mail.com         | password1 | "Email format is invalid"         |
      | @mailcom          | password1 | "Email format is invalid"         |
