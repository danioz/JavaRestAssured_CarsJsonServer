Feature: Car CRUD

  Background: Common setup for scenarios
    Given Setup base request specification without auth
     And User sends a valid POST request to login

  Scenario: C01 Newly created car by cars endpoint should have saved all provided details
            and could be asserted
    When Car request is updated with
      | brand | model | generation | color | code | displacement | cylinderSystem | power |
      | BMW   | 330   | 4          | black | 71   | 2996         | R6             | 500   |
     And User sends a valid POST request to Cars to create a new car
    Then Response status should be 201
     And Car should have data
       | brand | model | generation | color | code | displacement | cylinderSystem | power |
       | BMW   | 330   | 4          | black | 71   | 2996         | R6             | 500   |
    When User sends a valid GET request to Cars looking for car by Id
    Then Response status should be 200
     And Car should have data
      | brand | model | generation | color | code | displacement | cylinderSystem | power |
      | BMW   | 330   | 4          | black | 71   | 2996         | R6             | 500   |

  Scenario: C02 Updated car by cars endpoint should have saved all provided details
    When User sends a valid POST request to Cars to create a new car
     And Car request is updated with
       | brand | model | generation | color | code | displacement | cylinderSystem | power |
       | BMW   | 335   | 5          | black | 77   | 3496         | V8             | 900   |
     And User sends a valid PUT request to Cars looking for created car by Id to update
    Then Response status should be 200
     And Car should have data
       | brand | model | generation | color | code | displacement | cylinderSystem | power |
       | BMW   | 335   | 5          | black | 77   | 3496         | V8             | 900   |

  Scenario: C03 Deleted car by cars endpoint should return success status
    When User sends a valid POST request to Cars to create a new car
     And User sends a valid DELETE request to Cars looking for created car by Id to delete
    Then Response status should be 200

  Scenario: C04 Deleted car by cars endpoint should not be able to found by Id
    When User sends a valid POST request to Cars to create a new car
     And User sends a valid DELETE request to Cars looking for created car by Id to delete
     And User sends a valid GET request to Cars looking for car by Id
    Then Response status should be 404
     And Deleted car should have no body - {}

  Scenario: C05 Found BMW 750i at cars endpoint should have a proper engine data (HOMEWORK)
    When User sends a valid GET request to Cars to get all cars
    Then Response status should be 200
     And Cars response should have proper cars details
       | brand | model | generation | displacement | cylinderSystem | power |
       | BMW   | 750i  | 6          | 4395         | V8             | 450   |

  Scenario: C06 Found Cars at cars endpoint should have a proper engine data
    When User sends a valid GET request to Cars to get all cars
    Then Response status should be 200
     And Cars response should have proper cars details
      | brand | model  | generation | displacement | cylinderSystem | power |
      | BMW   | 750i   | 6          | 4395         | V8             | 450   |
      | BMW   | 740i   | 6          | 2998         | R6             | 326   |
      | BMW   | M760Li | 6          | 6592         | V12            | 610   |

  Scenario: C07 Returned cars at cars endpoint should have only specified brand
    When User defines request with query parameter "brand" and value "BMW"
     And User defines request with query parameter "engine.power" and value "326"
     And User sends a valid GET request to Cars to get all cars
    Then Response status should be 200
     And Cars response should contains entered data
       | brand | model |
       | BMW   | 740i  |
       | BMW   | 330   |
