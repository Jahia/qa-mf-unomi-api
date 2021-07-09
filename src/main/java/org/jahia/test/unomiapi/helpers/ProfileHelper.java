package org.jahia.test.unomiapi.helpers;

import java.net.URL;
import java.util.Map;

import org.apache.unomi.api.Profile;
import org.apache.unomi.persistence.spi.CustomObjectMapper;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.specification.RequestSpecification;

public class ProfileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileHelper.class);
    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public ProfileHelper(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    public Profile getProfile(String user, String password, String profileId) {
        final String profileUrl = TestGlobalConfiguration.getUnomiUrl() + "/cxs/profiles/" + profileId;
        try {
            RequestSpecification req = buildProfileRequestSpec(user, password);

            RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
            unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                    new URL(profileUrl), null, HttpMethod.GET));

            return CustomObjectMapper.getObjectMapper().readValue(unomiApiScenarioRuntimeData.getResponse().asString(),
                    Profile.class);
        } catch (Exception e) {
            LOGGER.error("Unable to resolve profile with url {}", profileUrl, e);
            return null;
        }
    }

    public int getNbProfiles(String user, String password) throws Throwable {
        RequestSpecification req = buildProfileRequestSpec(user, password);

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(
                reqHelper.sendRequest(req, new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/profiles/count"), null, HttpMethod.GET));

        return Integer.parseInt(unomiApiScenarioRuntimeData.getResponse().asString());
    }

    private RequestSpecification buildProfileRequestSpec(String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

    public boolean doesProfileContainsProperty(Profile profile, String property, String expectedValue) throws Throwable {

        String actualValue = String.valueOf(profile.getProperty(property));
        if (actualValue == null)
            return false;
        return actualValue.equals(expectedValue);
    }

    public boolean doesProfileContainInterest(Profile profile, String interest, int expectedValue) throws Throwable {

        Map<String, Integer> interests = (Map<String, Integer>) profile.getProperty("interests");

        if (interests == null || interests.get(interest) == null)
            return false;
        return interests.get(interest).equals(expectedValue);
    }

    // Will look for current profile in the unomiApiScenarioRuntimeData
    public boolean doesProfileContainsProperty(String property, String expectedValue) throws Throwable {
        Profile profile = CustomObjectMapper.getObjectMapper().readValue(unomiApiScenarioRuntimeData.getResponse().asString(),
                Profile.class);

        String actualValue = (String) profile.getProperty(property);
        return actualValue.equals(expectedValue);
    }

    // Will be moved later
    // Assertions should be made in cucumber glue assertion steps "Then"
    public void assertProfileContainsProperties(Map<String, String> propertiesAndValues) throws Throwable {
        Profile profile = CustomObjectMapper.getObjectMapper().readValue(unomiApiScenarioRuntimeData.getResponse().asString(),
                Profile.class);

        for (Map.Entry<String, String> entry : propertiesAndValues.entrySet()) {
            String expectedProperty = entry.getKey();
            String expectedValue = entry.getValue();

            String actualValue = (String) profile.getProperty(expectedProperty);
            Assert.assertEquals(actualValue, expectedValue);
        }
    }

}
