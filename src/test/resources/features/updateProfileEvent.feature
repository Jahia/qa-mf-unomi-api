Feature: Update Profile event

  @unomiapi
  Scenario: Update profile event during perso request
    Given I get the "home" page from DX server
    And I extract the ids "siteID, pageID" from the response
    And I extract the ids "wem-session-id,JSESSIONID" from the response headers
    And I define a unomi context event with the following parameters
      | eventType         | view                                   |
      | pageName          | Home                                   |
      | pagePath          | /sites/JahiaMfIntegTests/home          |
      | templateName      | home                                   |
      | categories        | /sites/systemsite/categories/qacathome |
      | tags              | home                                   |
      | isContentTemplate | false                                  |
    And I post a context request to Unomi server with the previously defined elements
    And I extract the ids "profileId" from the response
    And I get the "perso-on-profile-property-conten" page from DX server
    And I extract the ids "pageID" from the response
    And I extract the variant id corresponding to the displayable name "qa automaton" from the response
    And I define a unomi context event with the following parameters
      | eventType    | view                                                           |
      | pageName     | perso-on-profile-property-content                              |
      | pagePath     | /sites/JahiaMfIntegTests/home/perso-on-profile-property-conten |
      | templateName | simple                                                         |
    And I define a unomi context event with the following parameters
      | eventType            | updateProperties |
      | updateType           | add              |
      | properties.firstName | qa               |
      | properties.lastName  | automaton        |
    When I post a context request to Unomi server with the previously defined elements
    And I get the "perso-on-profile-property-conten" page from DX server
    Then I extract the displayed variant 2 id from the response
    And The displayed variant id corresponds to the variant id of "qa automaton"
