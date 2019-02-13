Feature: Unomi API

  @unomiapi
  Scenario: Perso on profile property using the API
    Given I get the "home" page from DX server using REST
    And I extract the ids "siteID, pageID" from the response
    And I extract the ids "wem-session-id" from the response headers
    And I define a unomi context event with the following parameters
      | EventType | view                          |
      | PageName  | Home                          |
      | PagePath  | /sites/JahiaMfIntegTests/home |
    And I post a context request to Unomi server with the previously defined elements and the following source
      | PageName | Home                          |
      | PagePath | /sites/JahiaMfIntegTests/home |
    And I extract the ids "context-profile-id" from the response headers
    Given I get the "perso-on-profile-property-conten" page from DX server using REST
    And I extract the ids "pageID" from the response
    And I extract the personalization 2 from the response
    And I define a unomi context event with the following parameters
      | EventType | view                                                           |
      | PageName  | perso-on-profile-property-content                              |
      | PagePath  | /sites/JahiaMfIntegTests/home/perso-on-profile-property-conten |
    And I define a unomi context event with the following parameters
      | EventType  | updateProperties |
      | UpdateType | add              |
      | FirstName  | qa               |
      | LastName   | automaton        |
    And I post a context request to Unomi server with the previously defined elements and the following source
      | PageName | perso-on-profile-property-content                              |
      | PagePath | /sites/JahiaMfIntegTests/home/perso-on-profile-property-conten |
    Then The first personalization in the response returns 2 variants ids
