@Sample
Feature: Testing GG lite installation

  Background:
    #Given my device is registered as a Thing
    And I install Greengrass Lite


  Scenario: As a developer, I can create a component in Cloud and deploy it on my device
    When I create a Greengrass deployment with components
        | com.aws.HelloWorld | classpath:/greengrass/components/recipes/hello_world_recipe.yaml |