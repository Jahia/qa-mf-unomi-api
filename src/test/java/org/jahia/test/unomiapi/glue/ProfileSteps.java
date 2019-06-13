package org.jahia.test.unomiapi.glue;

import java.util.Map;

import org.apache.unomi.api.Profile;
import org.jahia.test.commonutils.misc.BaseSteps;
import org.jahia.test.commonutils.misc.Util;
import org.jahia.test.commonutils.seleniumutils.BrowserHelper;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.jahia.test.unomiapi.helpers.ProfileHelper;
import org.testng.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

public class ProfileSteps extends BaseSteps {

    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;
    private BrowserHelper browserHelper;

    public ProfileSteps(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData, BrowserHelper browserHelper) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
        this.browserHelper = browserHelper;
    }

    Profile profile;

    @When("^I get the current profile properties using the Unomi API and user \"([^\"]*)\" \"([^\"]*)\"$")
    public void i_get_the_current_profile_properties_using_the_Unomi_API_and_user(String user, String password) throws Throwable {
        // call to the qa mf unomi api test method
        ProfileHelper profileHelper = new ProfileHelper(unomiApiScenarioRuntimeData);
        profile = profileHelper.getProfile(user, password, browserHelper.getCookieValue("wem-profile-id"));
        Assert.assertNotNull(profile, String.format("Could not retrieve profile corresponding to %s . Check the logs",
                browserHelper.getCookieValue("wem-profile-id")));
    }

    @Then("^The profile properties retrieved with the API contains the following properties and values$")
    public void the_profile_properties_retrieved_with_the_API_contains_the_following_properties_and_values(DataTable propertiesDt)
            throws Throwable {
        ProfileHelper profileHelper = new ProfileHelper(unomiApiScenarioRuntimeData);

        for (Map.Entry<String, String> entry : Util
                .convertCucumberDtToMap(propertiesDt, unomiApiScenarioRuntimeData.getScenarioStartTimeMillis()).entrySet()) {
            String property = entry.getKey();
            String expectedValue = entry.getValue();

            // call to the qa mf unomi api test method
            Assert.assertTrue(profileHelper.doesProfileContainsProperty(profile, property, expectedValue),
                    String.format("Property %s with value %s not found in the profile, check the logs", property, expectedValue));
        }

    }

    @Then("^The profile properties retrieved with the API does not contain the following properties and values$")
    public void the_profile_properties_retrieved_with_the_API_does_not_contain_the_following_properties_and_values(DataTable propertiesDt)
            throws Throwable {
        ProfileHelper profileHelper = new ProfileHelper(unomiApiScenarioRuntimeData);

        for (Map.Entry<String, String> entry : Util
                .convertCucumberDtToMap(propertiesDt, unomiApiScenarioRuntimeData.getScenarioStartTimeMillis()).entrySet()) {
            String property = entry.getKey();
            String expectedValue = entry.getValue();

            // call to the qa mf unomi api test method
            Assert.assertFalse(profileHelper.doesProfileContainsProperty(profile, property, expectedValue),
                    String.format("Property %s with value %s found in the profile, check the logs", property, expectedValue));
        }

    }

}
