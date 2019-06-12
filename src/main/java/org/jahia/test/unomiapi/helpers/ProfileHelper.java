package org.jahia.test.unomiapi.helpers;

import java.net.URL;
import java.util.Map;

import org.apache.unomi.api.Profile;
import org.apache.unomi.persistence.spi.CustomObjectMapper;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiTestRtVariables;
import org.testng.Assert;

import com.mashape.unirest.http.HttpMethod;

import io.restassured.specification.RequestSpecification;

public class ProfileHelper {

    private UnomiApiTestRtVariables unomiApiTestRtVariables;

    public ProfileHelper(UnomiApiTestRtVariables unomiApiTestRtVariables) {
        this.unomiApiTestRtVariables = unomiApiTestRtVariables;
    }

    public Profile getProfile(String user, String password, String profileId) throws Throwable {
        RequestSpecification req = buildProfileRequestSpec(user, password);

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiTestRtVariables);
        unomiApiTestRtVariables.response = reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/profiles/" + profileId), null, HttpMethod.GET);

        Profile profile = CustomObjectMapper.getObjectMapper().readValue(unomiApiTestRtVariables.response.asString(), Profile.class);

        return profile;
    }

    private RequestSpecification buildProfileRequestSpec(String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiTestRtVariables);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

    public boolean doesProfileContainsProperty(Profile profile, String property, String expectedValue) throws Throwable {

        String actualValue = String.valueOf(profile.getProperty(property));
        if (actualValue == null)
            return false;
        return actualValue.equals(expectedValue);
    }

    // Will look for current profile in the UnomiApiTestRtVariables
    public boolean doesProfileContainsProperty(String property, String expectedValue) throws Throwable {
        Profile profile = CustomObjectMapper.getObjectMapper().readValue(unomiApiTestRtVariables.response.asString(), Profile.class);

        String actualValue = (String) profile.getProperty(property);
        return actualValue.equals(expectedValue);
    }

    // Will be moved later
    // Assertions should be made in cucumber glue assertion steps "Then"
    public void assertProfileContainsProperties(Map<String, String> propertiesAndValues) throws Throwable {
        Profile profile = CustomObjectMapper.getObjectMapper().readValue(unomiApiTestRtVariables.response.asString(), Profile.class);

        for (Map.Entry<String, String> entry : propertiesAndValues.entrySet()) {
            String expectedProperty = entry.getKey();
            String expectedValue = entry.getValue();

            String actualValue = (String) profile.getProperty(expectedProperty);
            Assert.assertEquals(actualValue, expectedValue);
        }
    }
}
