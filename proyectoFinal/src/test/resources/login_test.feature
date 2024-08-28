Feature: Access the login page
  Scenario: User enters the login page URL
    When the client calls endpoint '/login'
    Then the client receives status code of 200
    And the client receives HTML page as response

  Scenario: User enters non-existant credentials when login
    When the client calls 'POST' endpoint '/api/v1/auth' with username 'wronguser' and password 'wrongpass'
    Then the client receives status code of 404
    And the client receives message "User not found with USERNAME: 'wronguser'"

  Scenario: User enters wrong credentials when login
    When the client calls 'POST' endpoint '/api/v1/auth' with username 'admin' and password 'wrongpass'
    Then the client receives status code of 403
    And the client receives message "Forbidden"

  Scenario: User enters correct credentials when login
    When the client calls 'POST' endpoint '/api/v1/auth' with username 'admin' and password 'admin'
    Then the client receives status code of 200
    And the client receives message 'Usuario logueado correctamente!'