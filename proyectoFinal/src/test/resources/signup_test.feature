Feature: Access the signup page
  Scenario: User enters the login page URL
    When the client calls endpoint '/signup'
    Then the client receives status code of 200
    And the client receives HTML page as response
