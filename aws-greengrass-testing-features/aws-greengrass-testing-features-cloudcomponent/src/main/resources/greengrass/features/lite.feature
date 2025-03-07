@Sample
Feature: Testing GG lite installation

  Background:
    Given my device is registered as a lite Thing

  Scenario: As a developer, I can install greengrass lite and deploy a component on my core device
    And I install Greengrass Lite
    When I create a Greengrass deployment with components
      | com.aws.HelloWorld | classpath:/greengrass/components/recipes/hello_world_lite.yaml |
    And I deploy the Greengrass deployment configuration to thing group
    Then the Greengrass deployment is COMPLETED on the device after 180 seconds
    And the com.aws.HelloWorld systemd log on the device contains the line "Hello from my custom lite component" within 20 seconds