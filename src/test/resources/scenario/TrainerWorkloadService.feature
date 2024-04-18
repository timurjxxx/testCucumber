Feature: Update Trainer Workload

  Scenario: Add Training Duration for New Trainer
    Given a new trainer with the username "johndoe"
    When a training duration of 60 minutes is added for the current date
    Then the trainer's workload should be updated
    And the trainer should be saved to the database

  Scenario: Update Training Duration for Existing Trainer
    Given an existing trainer with the username "existingtrainer"
    And a training duration of 90 minutes is already recorded for the current month
    When a training duration of 60 minutes is added for the current date
    Then the existing trainer's workload should be updated
    And the trainer should be saved to the database
