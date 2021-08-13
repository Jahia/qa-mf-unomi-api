package org.jahia.test.unomiapi.helpers;

import com.mashape.unirest.http.HttpMethod;
import io.restassured.specification.RequestSpecification;
import org.jahia.test.unomiapi.data.TestGlobalConfiguration;
import org.jahia.test.unomiapi.data.UnomiApiScenarioRuntimeData;
import org.jahia.selenium;
import org.json.JSONObject;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class EventHelper {

    private UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData;

    public EventHelper(UnomiApiScenarioRuntimeData unomiApiScenarioRuntimeData) {
        this.unomiApiScenarioRuntimeData = unomiApiScenarioRuntimeData;
    }

    public int getNumberOfViewForPage(String partialPagePath, String user, String password) throws Throwable {

        RequestSpecification req = buildNbofViewRequestSpec(partialPagePath, user, password);

        Instant now = Instant.now();
        Long minTime = now.minus(1, ChronoUnit.DAYS).toEpochMilli();
        Long maxTime = now.plus(1, ChronoUnit.DAYS).toEpochMilli();

        JSONObject conditionParameters = new JSONObject();
        conditionParameters.put("propertyName", "timeStamp");
        conditionParameters.put("comparisonOperator", "between");
        conditionParameters.put("propertyValues", Arrays.asList(minTime, maxTime));

        JSONObject condition = new JSONObject();
        condition.put("type", "sessionPropertyCondition");
        condition.put("parameterValues", conditionParameters);

        JSONObject query = new JSONObject();
        query.put("condition", condition);

        String requestBodyJson = query.toString();

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        unomiApiScenarioRuntimeData.setResponse(reqHelper.sendRequest(req,
                new URL(TestGlobalConfiguration.getUnomiUrl() + "/cxs/query/event/target.properties.pageInfo.destinationURL"),
                requestBodyJson, HttpMethod.POST));

        String response = this.unomiApiScenarioRuntimeData.getResponse().asString();



        // TODO : read response as object
        // JSONObject responseJson = new JSONObject(response);
        // Here we have only a partialPath (ex: "JahiaMfIntegTests/home/perso-on-goal-page.html" instead of
        // "http://localhost:8080/sites/JahiaMfIntegTests/home/perso-on-goal-page.html")
        // A simple get("http://localhost:8080/sites/JahiaMfIntegTests/home/perso-on-goal-page.html") does not work
        // A solution i was trying was to pass JahiaSeleniumConfiguration.getPropertyValue("Dselenium.jahia.host","") but JahiaSeleniumConfiguration
        // is not recognized
        // impacted feature to test : mostVisitedPage.feature
        // branch for the qa-mf-selenium side of this task  https://github.com/Jahia/qa-mf-selenium/tree/DMF-4868_RemoveUITestMostVisitedPages

        if(response.indexOf(partialPagePath) <0 ) {return 0; }
        else {
            String test = response.split(partialPagePath + "\":")[1];
            return Integer.parseInt(test.split(",")[0]);
        }
    }

    private RequestSpecification buildNbofViewRequestSpec(String pagePath, String user, String password) {
        RequestSpecification req;

        RestRequestHelper reqHelper = new RestRequestHelper(unomiApiScenarioRuntimeData);
        req = reqHelper.buildRequest(user, password);
        return req;
    }

}
