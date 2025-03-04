@Sample
Feature: Testing GG lite installation

  Background:
    And I install Greengrass Lite


  Scenario: As a developer, I can create a component in Cloud and deploy it on my device
    Given my device is registered as a Thing