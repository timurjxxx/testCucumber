Feature: Update Trainer Workload

  Scenario: Update Trainer Workload with Add Action
    Given a trainer workload update request with type "ADD"
    When I send the update request
    Then the workload should add training  duration successfully
    And the response status code should be 200



