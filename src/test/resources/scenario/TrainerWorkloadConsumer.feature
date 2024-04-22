Feature: Trainer Workload Consumer

  Scenario: Valid Trainer Workload Message
    Given a valid trainer workload message
    When the message is received by the consumer
    Then the workload should be updated
    And the message should not be sent to the dead-letter queue

  Scenario: Invalid Trainer Workload Message
    Given an invalid trainer workload message
    When the message is received by the consumer
    Then the message should be sent to the dead-letter queue

  Scenario: Bad Trainer Workload Message
    Given a bad trainer workload message
    When the message is received by the consumer
    Then the message should be rejected
    And an error should be logged