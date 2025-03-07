@Sample
Feature: Testing GG lite installation

  Background:
    Given my device is registered as a lite Thing
    And I install Greengrass Lite

  Scenario: As a developer, I can create a component in Cloud and deploy it on my device
    When I create a Greengrass deployment with components
      | com.aws.HelloWorldLite | classpath:/greengrass/components/recipes/hello_world_lite_recipe.yaml |
    And I deploy the Greengrass deployment configuration
    Then the Greengrass deployment is COMPLETED on the device after 180 seconds
    And the com.aws.HelloWorldMultiplatform log on the device contains the line "Hello World!" within 20 seconds