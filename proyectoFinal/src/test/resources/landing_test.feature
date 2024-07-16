Feature: Access the landing page
  Scenario: User enters the landing page URL
    When the client calls landing page endpoint
    Then the client receives status code of 200
    And the client receives HTML page as response
