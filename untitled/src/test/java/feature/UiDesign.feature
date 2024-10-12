@Nagp

Feature: Ui Design feature

  @Nagp
  Scenario: verify UI of landing page of application
    Given user is on Ui landing page
    And verify user is on landing page and welcome guideline message is visible
    Then verify content of landing page

  @Nagp
  Scenario: verify HanBurger menu of landing page of application
    Given verify HamBurger menu of landing page of application
    And user click on HamBurger menu of Ui landing page
    Then verify menu content

  @Nagp
  Scenario: verify Animation page of application
    And user click on HamBurger menu of Ui landing page
    Given user click on animation of Ui landing page
    When verify content of animation page

  @Nagp
  Scenario: verify Arc menu page of application
    And user click on HamBurger menu of Ui landing page
    Given user click on Arc Menu
    Then verify content of Arc page

  @Nagp
  Scenario: verify Chart page of application
    And user click on HamBurger menu of Ui landing page
    Given user click on Chart Section
